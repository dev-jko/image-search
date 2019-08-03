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
    }


    @Singleton
    class ViewModelImpl @Inject constructor(

    ) : ViewModel(), Inputs, Outputs {

        private val querySubmitted: PublishSubject<String> = PublishSubject.create()
        private val query: Observable<String> = this.querySubmitted.throttleFirst(1000, TimeUnit.MILLISECONDS)

        val inputs: Inputs = this
        val outputs: Outputs = this

        override fun query(): Observable<String> = this.query

        override fun querySubmitted(query: String) {
            this.querySubmitted.onNext(query)
        }

    }
}