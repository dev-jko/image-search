package com.nadarm.imagesearch.presenter.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.useCase.GetImageDocuments
import com.nadarm.imagesearch.presenter.view.adapter.ImageAdapter
import io.reactivex.Observable
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
    }

    interface Outputs {
        fun imageDocuments(): Observable<List<ImageDocument>>
        fun displayProgress(): Observable<Int>
        fun startDetailFragment(): Observable<ImageDocument>
        fun restorePosition(): Observable<Int>
    }

    @Singleton
    class ViewModelImpl @Inject constructor(
        private val getImageDocuments: GetImageDocuments
    ) : ViewModel(), Inputs, Outputs {

        private val query: PublishSubject<String> = PublishSubject.create()
        private val imageClicked: PublishSubject<ImageDocument> = PublishSubject.create()
        private val savePosition: PublishSubject<Int> = PublishSubject.create()

        private val imageDocuments: BehaviorSubject<List<ImageDocument>> = BehaviorSubject.create()
        private val displayProgress: BehaviorSubject<Int> = BehaviorSubject.create()
        private val startDetailFragment: Observable<ImageDocument> =
            this.imageClicked.throttleFirst(600, TimeUnit.MILLISECONDS)
        private val restorePosition: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {
            this.query.flatMapSingle {
                this.getImageDocuments.execute(it, 1, 0, 80)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { this.displayProgress.onNext(View.VISIBLE) }
                    .doFinally { this.displayProgress.onNext(View.GONE) }
            }
                .doOnNext { this.savePosition(0) }
                .subscribe(this.imageDocuments)

            this.savePosition.subscribe(this.restorePosition)
        }

        override fun imageDocuments(): Observable<List<ImageDocument>> = this.imageDocuments
        override fun displayProgress(): Observable<Int> = this.displayProgress
        override fun startDetailFragment(): Observable<ImageDocument> = this.startDetailFragment
        override fun restorePosition(): Observable<Int> = this.restorePosition

        override fun query(query: String) {
            this.query.onNext(query)
        }

        override fun imageClicked(imageDocument: ImageDocument) {
            this.imageClicked.onNext(imageDocument)
        }

        override fun savePosition(position: Int) {
            this.savePosition.onNext(position)
        }
    }
}