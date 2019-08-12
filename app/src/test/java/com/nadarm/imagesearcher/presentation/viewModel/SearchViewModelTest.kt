package com.nadarm.imagesearcher.presentation.viewModel

import android.database.Cursor
import com.nadarm.imagesearcher.di.AppSchedulers
import com.nadarm.imagesearcher.domain.useCase.AddSearchedQuery
import com.nadarm.imagesearcher.domain.useCase.GetSuggestions
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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit

class SearchViewModelTest {

    private lateinit var vm: SearchViewModel.ViewModelImpl
    private lateinit var testScheduler: TestScheduler
    private val addSearchQuery: AddSearchedQuery = mock(AddSearchedQuery::class.java)
    private val getSuggestions: GetSuggestions = mock(GetSuggestions::class.java)
    private val schedulers: AppSchedulers = mock(AppSchedulers::class.java)
    private val compositeDisposable = CompositeDisposable()
    private val textList = arrayOf("k", "ka", "kak", "kaka", "kakao")

    private lateinit var test: TestObserver<Cursor>

    @Before
    fun setUp() {
        this.testScheduler = TestScheduler()
        `when`(this.schedulers.ui()).thenReturn(this.testScheduler)
        `when`(this.schedulers.io()).thenReturn(this.testScheduler)
        `when`(this.schedulers.computation()).thenReturn(this.testScheduler)

        this.vm = SearchViewModel.ViewModelImpl(this.addSearchQuery, this.getSuggestions, this.schedulers)
        test = TestObserver()
        test.addTo(compositeDisposable)
    }

    @After
    fun tearDown() {
        this.compositeDisposable.clear()
    }


    @Test
    fun `test query suggestions whit error`() {
        val throwable = Throwable()
        `when`(this.getSuggestions.execute(anyString())).thenReturn(Single.error(throwable))


        this.vm.outputs.querySuggestions()
            .observeOn(schedulers.ui())
            .subscribe(test)

        this.vm.inputs.queryChanged(this.textList.first())


        test.assertSubscribed()
            .assertNoValues()

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        test.assertError(throwable)
            .assertNoValues()
            .assertNotComplete()
    }


    @Test
    fun `test query suggestions with slow inputs`() {
        val cursor: Cursor = mock(Cursor::class.java)
        `when`(this.getSuggestions.execute(anyString())).thenReturn(Single.just(cursor))


        this.vm.outputs.querySuggestions()
            .observeOn(schedulers.ui())
            .subscribe(test)

        val queryObservable = textList.toObservable()
        Observable.interval(0, 600, TimeUnit.MILLISECONDS, testScheduler)
            .zipWith(queryObservable) { _, text -> text }
            .subscribe(this.vm.inputs::queryChanged)
            .addTo(compositeDisposable)


        test.assertSubscribed()
            .assertValueCount(0)

        testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)

        testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS)
        test.assertValueCount(2)

        testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS)
        test.assertValueCount(3)

        testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS)
        test.assertValueCount(4)

        testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS)
        test.assertValueCount(5)

        testScheduler.advanceTimeBy(1000000, TimeUnit.MILLISECONDS)
        test.assertValueCount(5)

        test.assertNotComplete()
            .assertNoErrors()

        this.textList.forEach {
            verify(this.getSuggestions).execute(it)
        }
    }

    @Test
    fun `test query suggestions with fest inputs`() {
        val queryObservable = textList.toObservable()
        Observable.interval(0, 100, TimeUnit.MILLISECONDS, testScheduler)
            .zipWith(queryObservable) { _, text -> text }
            .subscribe(this.vm.inputs::queryChanged)
            .addTo(compositeDisposable)
        val cursor: Cursor = mock(Cursor::class.java)
        `when`(this.getSuggestions.execute(anyString())).thenReturn(Single.just(cursor))


        this.vm.outputs.querySuggestions()
            .observeOn(schedulers.ui())
            .subscribe(test)

        test.assertSubscribed()
            .assertValueCount(0)

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        test.assertValueCount(0)

        testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)

        testScheduler.advanceTimeBy(1000000, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)

        test.assertNotComplete()
            .assertNoErrors()

        verify(this.getSuggestions).execute(this.textList.last())
    }


}