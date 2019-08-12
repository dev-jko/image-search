package com.nadarm.imagesearcher.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.nadarm.imagesearcher.di.AppSchedulers
import com.nadarm.imagesearcher.domain.model.ImageDocument
import com.nadarm.imagesearcher.domain.useCase.GetQueryResponse
import com.nadarm.imagesearcher.presentation.view.adapter.DetailPagerAdapter
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

interface DetailViewModel {

    interface Inputs : DetailPagerAdapter.Delegate {
        fun selectedImage(imageDocument: ImageDocument)
    }

    interface Outputs {
        fun loadImageDocument(): Observable<ImageDocument>
        fun imageDocuments(): Observable<List<ImageDocument>>
        fun currentPageAndIndex(): Observable<Pair<Int, Int>>
        fun openUrlLink(): Observable<String>
    }

    @Singleton
    class ViewModelImpl @Inject constructor(
        private val getQueryResponse: GetQueryResponse,
        private val schedulers: AppSchedulers
    ) : ViewModel(), Inputs, Outputs {

        private val selectedImage: PublishSubject<ImageDocument> = PublishSubject.create()
        private val linkClicked: PublishSubject<String> = PublishSubject.create()

        private val loadImageDocument: BehaviorSubject<ImageDocument> = BehaviorSubject.create()
        private val imageDocuments: BehaviorSubject<List<ImageDocument>> = BehaviorSubject.create()
        private val currentPageAndIndex: BehaviorSubject<Pair<Int, Int>> = BehaviorSubject.create()
        private val openUrlLink: Observable<String> =
            this.linkClicked.throttleFirst(600, TimeUnit.MILLISECONDS, schedulers.computation())

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {
            this.selectedImage
                .subscribe(this.loadImageDocument)

            this.selectedImage
                .flatMapSingle {
                    this.getQueryResponse.execute(it.query, it.page)
                        .subscribeOn(schedulers.io())
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
        override fun openUrlLink(): Observable<String> = this.openUrlLink

        override fun selectedImage(imageDocument: ImageDocument) {
            this.selectedImage.onNext(imageDocument)
        }

        override fun linkClicked(url: String) {
            this.linkClicked.onNext(url)
        }
    }

}