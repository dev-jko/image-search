package com.nadarm.imagesearch.data.api

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofitTest {

    private lateinit var service: ApiService
    private val baseUrl = "https://dapi.kakao.com/"

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        this.service = retrofit.create(ApiService::class.java)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun searchImage() {
        val result = this.service.searchImage(mapOf<String, String>("query" to "안드로이드"))
        result.test()
            .values()
            .forEach {
                println(it.meta!!.totalCount)
                println(it.documents!![0].displaySitename)
                println(it.documents!![0].thumbnailUrl)
            }
    }
}