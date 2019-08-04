package com.nadarm.imagesearch.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.nadarm.imagesearch.util.MySearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

interface SearchViewModel {

    interface Inputs : MySearchView.Delegate {

    }

    interface Outputs {
        fun query(): Observable<String>
        fun querySuggestions(): Observable<List<String>>
    }


    @Singleton
    class ViewModelImpl @Inject constructor(

    ) : ViewModel(), Inputs, Outputs {

        private val querySubmitted: PublishSubject<String> = PublishSubject.create()
        private val queryChanged: PublishSubject<String> = PublishSubject.create()

        private val query: Observable<String> = this.querySubmitted.throttleFirst(1000, TimeUnit.MILLISECONDS)
        private val querySuggestions: Observable<List<String>>

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

            this.querySuggestions = Observable.just(listOf("추천 단어", "추천2"))

        }

        override fun query(): Observable<String> = this.query
        override fun querySuggestions(): Observable<List<String>> = this.querySuggestions

        override fun querySubmitted(query: String) {
            this.querySubmitted.onNext(query)
        }

        override fun queryChanged(text: String) {
            this.queryChanged.onNext(text)
        }

    }
}