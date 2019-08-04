package com.nadarm.imagesearch.presenter.viewModel

import android.database.Cursor
import androidx.lifecycle.ViewModel
import com.nadarm.imagesearch.domain.useCase.AddSearchedQuery
import com.nadarm.imagesearch.domain.useCase.GetSuggestions
import com.nadarm.imagesearch.util.MySearchView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

interface SearchViewModel {

    interface Inputs : MySearchView.Delegate {

    }

    interface Outputs {
        fun query(): Observable<String>
        fun querySuggestions(): Observable<Cursor>
    }


    @Singleton
    class ViewModelImpl @Inject constructor(
        private val addSearchedQuery: AddSearchedQuery,
        private val getSuggestions: GetSuggestions
    ) : ViewModel(), Inputs, Outputs {

        private val compositeDisposable = CompositeDisposable()

        private val querySubmitted: PublishSubject<String> = PublishSubject.create()
        private val queryChanged: PublishSubject<String> = PublishSubject.create()

        private val query: Observable<String> = this.querySubmitted.throttleFirst(1000, TimeUnit.MILLISECONDS)
        private val querySuggestions: Observable<Cursor>

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {
            this.querySubmitted
                .flatMapCompletable {
                    this.addSearchedQuery.execute(it)
                        .subscribeOn(Schedulers.io())
                }
                .subscribe()
                .addTo(compositeDisposable)

            this.querySuggestions = this.queryChanged
                .debounce(600, TimeUnit.MILLISECONDS)
                .flatMapSingle {
                    this.getSuggestions.execute(it)
                        .subscribeOn(Schedulers.io())
                }
        }

        override fun query(): Observable<String> = this.query
        override fun querySuggestions(): Observable<Cursor> = this.querySuggestions

        override fun querySubmitted(query: String) {
            this.querySubmitted.onNext(query)
        }

        override fun queryChanged(text: String) {
            this.queryChanged.onNext(text)
        }

        override fun onCleared() {
            super.onCleared()
            this.compositeDisposable.clear()
        }
    }
}