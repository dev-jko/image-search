package com.nadarm.imagesearch.presenter.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.useCase.GetQueryResponse
import com.nadarm.imagesearch.presenter.model.SealedViewHolderData
import com.nadarm.imagesearch.presenter.model.mapper.SealedViewHolderDataMapper
import com.nadarm.imagesearch.presenter.view.adapter.ImageAdapter
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

interface ListViewModel {

    interface Inputs : ImageAdapter.Delegate {
        fun query(query: String)
        fun savePosition(position: Int)
        fun retrySearch()
    }

    interface Outputs {
        fun itemList(): Observable<List<SealedViewHolderData>>
        fun displayProgress(): Observable<Int>
        fun startDetailFragment(): Observable<ImageDocument>
        fun restorePosition(): Observable<Int>
        fun showSnackBar(): Observable<Unit>
    }

    @Singleton
    class ViewModelImpl @Inject constructor(
        private val getQueryResponse: GetQueryResponse,
        private val mapper: SealedViewHolderDataMapper
    ) : ViewModel(), Inputs, Outputs {

        private val compositeDisposable = CompositeDisposable()

        private val query: PublishSubject<Pair<String, Int>> = PublishSubject.create()
        private val imageClicked: PublishSubject<ImageDocument> = PublishSubject.create()
        private val savePosition: PublishSubject<Int> = PublishSubject.create()
        private val pageChangeButtonClicked: PublishSubject<Pair<String, Int>> = PublishSubject.create()
        private val retrySearch: PublishProcessor<Unit> = PublishProcessor.create()

        private val itemList: BehaviorSubject<List<SealedViewHolderData>> = BehaviorSubject.create()
        private val displayProgress: BehaviorSubject<Int> = BehaviorSubject.create()
        private val startDetailFragment: Observable<ImageDocument> =
            this.imageClicked.throttleFirst(600, TimeUnit.MILLISECONDS)
        private val restorePosition: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)
        private val showSnackBar: BehaviorSubject<Unit> = BehaviorSubject.create()
        private val retry: Flowable<Unit> = this.retrySearch.throttleFirst(1000, TimeUnit.MILLISECONDS)

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

            this.query
                .flatMapSingle { queryAndPage ->
                    this.getQueryResponse.execute(queryAndPage.first, queryAndPage.second)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe { this.displayProgress.onNext(View.VISIBLE) }
                        .doFinally { this.displayProgress.onNext(View.GONE) }
                        .doOnError { this.showSnackBar.onNext(Unit) }
                        .retryWhen { errors ->
                            errors.zipWith(this.retry) { o: Throwable, _: Unit -> Flowable.just(o) }
                        }
                        .map { mapper.mapToSealedViewHolderData(it, this) }
                }
                .doOnNext { this.savePosition(0) }
                .subscribe(this.itemList)

            this.pageChangeButtonClicked
                .throttleFirst(600, TimeUnit.MILLISECONDS)
                .subscribe(this.query)

            this.savePosition.subscribe(this.restorePosition)
        }

        override fun itemList(): Observable<List<SealedViewHolderData>> = this.itemList
        override fun displayProgress(): Observable<Int> = this.displayProgress
        override fun startDetailFragment(): Observable<ImageDocument> = this.startDetailFragment
        override fun restorePosition(): Observable<Int> = this.restorePosition
        override fun showSnackBar(): Observable<Unit> = this.showSnackBar

        override fun query(query: String) {
            this.query.onNext(query to 1)
        }

        override fun imageClicked(imageDocument: ImageDocument) {
            this.imageClicked.onNext(imageDocument)
        }

        override fun savePosition(position: Int) {
            this.savePosition.onNext(position)
        }

        override fun pageChangeButtonClicked(query: String, nextPage: Int) {
            this.pageChangeButtonClicked.onNext(query to nextPage)
        }

        override fun retrySearch() {
            this.retrySearch.onNext(Unit)
        }

        override fun onCleared() {
            super.onCleared()
            this.compositeDisposable.clear()
        }
    }
}