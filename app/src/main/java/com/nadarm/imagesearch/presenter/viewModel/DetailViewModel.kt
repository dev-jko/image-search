package com.nadarm.imagesearch.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.nadarm.imagesearch.domain.model.ImageDocument
import com.nadarm.imagesearch.domain.useCase.GetQueryResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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
        fun imageDocuments(): Observable<List<ImageDocument>>
        fun currentPageAndIndex(): Observable<Pair<Int, Int>>
    }

    @Singleton
    class ViewModelImpl @Inject constructor(
        private val getQueryResponse: GetQueryResponse
    ) : ViewModel(), Inputs, Outputs {

        private val selectedImage: PublishSubject<ImageDocument> = PublishSubject.create()

        private val loadImageDocument: BehaviorSubject<ImageDocument> = BehaviorSubject.create()
        private val imageDocuments: BehaviorSubject<List<ImageDocument>> = BehaviorSubject.create()
        private val currentPageAndIndex: BehaviorSubject<Pair<Int, Int>> = BehaviorSubject.create()

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {
            this.selectedImage
                .subscribe(this.loadImageDocument)

            this.selectedImage
                .flatMapSingle {
                    this.getQueryResponse.execute(it.query, it.page)
                        .subscribeOn(Schedulers.io())
                }
                .map { it.documents }
                .subscribe(this.imageDocuments)

            this.selectedImage
                .map { it.page to it.index }
                .subscribe(this.currentPageAndIndex)
        }

        override fun loadImageDocument(): Observable<ImageDocument> = this.loadImageDocument
        override fun imageDocuments(): Observable<List<ImageDocument>> = this.imageDocuments
        override fun currentPageAndIndex(): Observable<Pair<Int, Int>> = this.currentPageAndIndex

        override fun selectedImage(imageDocument: ImageDocument) {
            this.selectedImage.onNext(imageDocument)
        }
    }

}