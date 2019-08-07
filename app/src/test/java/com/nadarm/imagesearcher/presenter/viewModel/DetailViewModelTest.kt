package com.nadarm.imagesearcher.presenter.viewModel

import com.nadarm.imagesearcher.di.AppSchedulers
import com.nadarm.imagesearcher.domain.model.ImageDocument
import com.nadarm.imagesearcher.domain.model.QueryResponse
import com.nadarm.imagesearcher.domain.useCase.GetQueryResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit

class DetailViewModelTest {

    private lateinit var vm: DetailViewModel.ViewModelImpl
    private lateinit var testScheduler: TestScheduler
    private val getQueryResponse: GetQueryResponse = mock(GetQueryResponse::class.java)
    private val schedulers: AppSchedulers = mock(AppSchedulers::class.java)

    private val compositeDisposable = CompositeDisposable()


    @Before
    fun setUp() {
        this.testScheduler = TestScheduler()
        Mockito.`when`(this.schedulers.ui()).thenReturn(this.testScheduler)
        Mockito.`when`(this.schedulers.io()).thenReturn(this.testScheduler)
        Mockito.`when`(this.schedulers.computation()).thenReturn(this.testScheduler)

        this.vm = DetailViewModel.ViewModelImpl(this.getQueryResponse, this.schedulers)
    }

    @After
    fun tearDown() {
        this.compositeDisposable.clear()
    }


    @Test
    fun `test current page and index`() {
        val document = mock(ImageDocument::class.java)
        `when`(document.index).thenReturn(20)
        `when`(document.page).thenReturn(1)


        val test = TestObserver<Pair<Int, Int>>()
        test.addTo(compositeDisposable)

        this.vm.outputs.currentPageAndIndex()
            .subscribeOn(testScheduler)
            .subscribe(test)

        this.vm.inputs.selectedImage(document)


        test.assertSubscribed()
            .assertNoValues()

        testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
            .assertValue(document.page to document.index)
            .assertNoErrors()
            .assertNotComplete()
    }

    @Test
    fun `test imageDocuments`() {
        val queryResponse: QueryResponse = mock(QueryResponse::class.java)
        val document = mock(ImageDocument::class.java)
        val documents = listOf(document)
        `when`(queryResponse.documents).thenReturn(documents)
        `when`(document.query).thenReturn("string")
        `when`(document.page).thenReturn(1)
        `when`(this.getQueryResponse.execute(anyString(), anyInt())).thenReturn(Single.just(queryResponse))


        this.vm.inputs.selectedImage(documents[0])

        val test = TestObserver<List<ImageDocument>>()
        test.addTo(compositeDisposable)

        this.vm.outputs.imageDocuments()
            .subscribeOn(schedulers.io())
            .subscribe(test)


        test.assertSubscribed()
            .assertNoValues()

        testScheduler.advanceTimeBy(300, TimeUnit.MILLISECONDS)
        test.assertNoErrors()
            .assertNotComplete()
            .assertValueCount(1)
            .assertValue(documents)

        verify(this.getQueryResponse).execute(anyString(), anyInt())
    }


    @Test
    fun `test open url link`() {
        val urls = arrayOf("https://test.com", "https://test22.com")
        Observable.interval(0, 500, TimeUnit.MILLISECONDS, testScheduler)
            .zipWith(urls.toObservable()) { _: Long, url: String -> url }
            .subscribe(this.vm.inputs::linkClicked)
            .addTo(compositeDisposable)

        val test = TestObserver<String>()
        test.addTo(compositeDisposable)

        this.vm.outputs.openUrlLink()
            .observeOn(schedulers.ui())
            .subscribe(test)


        test.assertSubscribed()
            .assertValueCount(0)

        testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
            .assertValue(urls[0])

        testScheduler.advanceTimeBy(10000, TimeUnit.MILLISECONDS)
        test.assertNoErrors()
            .assertNotComplete()
            .assertValueCount(1)
            .assertValue(urls[0])
    }


}