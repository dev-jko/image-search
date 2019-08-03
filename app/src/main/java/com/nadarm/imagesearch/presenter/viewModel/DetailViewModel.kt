package com.nadarm.imagesearch.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.nadarm.imagesearch.domain.model.ImageDocument
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

interface DetailViewModel {

    interface Inputs {
        fun selectedImage(imageDocument: ImageDocument)
    }

    interface Outputs {
        fun loadImageDocument(): Observable<ImageDocument>
    }

    @Singleton
    class ViewModelImpl @Inject constructor(

    ) : ViewModel(), Inputs, Outputs {

        private val selectedImage: PublishSubject<ImageDocument> = PublishSubject.create()

        private val loadImageDocument: BehaviorSubject<ImageDocument> = BehaviorSubject.create()

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {
            this.selectedImage
                .subscribe(this.loadImageDocument)
        }

        override fun loadImageDocument(): Observable<ImageDocument> = this.loadImageDocument

        override fun selectedImage(imageDocument: ImageDocument) {
            this.selectedImage.onNext(imageDocument)
        }
    }

}