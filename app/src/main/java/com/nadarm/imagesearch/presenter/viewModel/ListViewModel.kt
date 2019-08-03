package com.nadarm.imagesearch.presenter.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.useCase.GetImageDocuments
import com.nadarm.imagesearch.presenter.view.adapter.ImageAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

interface ListViewModel {

    interface Inputs : ImageAdapter.Delegate {

    }

    interface Outputs {
        fun imageDocuments(): LiveData<List<ImageDocument>>
        fun displayProgress(): LiveData<Int>
        fun startDetailFragment(): Observable<ImageDocument>
    }

    @Singleton
    class ViewModelImpl @Inject constructor(
        private val getImageDocuments: GetImageDocuments
    ) : ViewModel(), Inputs, Outputs {

        private val imageClicked: PublishSubject<ImageDocument> = PublishSubject.create()

        private val imageDocuments: MutableLiveData<List<ImageDocument>> = MutableLiveData(emptyList())
        private val displayProgress: MutableLiveData<Int> = MutableLiveData(View.GONE)
        private val startDetailFragment: Observable<ImageDocument> = this.imageClicked

        private val compositeDisposable = CompositeDisposable()

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {
            this.getImageDocuments.execute("android", 1, 1, 20)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { this.displayProgress.postValue(View.VISIBLE) }
                .doFinally { this.displayProgress.postValue(View.GONE) }
                .subscribe(this.imageDocuments::postValue)
                .addTo(compositeDisposable)
        }

        override fun imageDocuments(): LiveData<List<ImageDocument>> = this.imageDocuments
        override fun displayProgress(): LiveData<Int> = this.displayProgress
        override fun startDetailFragment(): Observable<ImageDocument> = this.startDetailFragment

        override fun imageClicked(imageDocument: ImageDocument) {
            this.imageClicked.onNext(imageDocument)
        }

        override fun onCleared() {
            super.onCleared()
            this.compositeDisposable.clear()
        }
    }
}