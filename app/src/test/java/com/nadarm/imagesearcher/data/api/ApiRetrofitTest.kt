package com.nadarm.imagesearcher.data.api

import com.nadarm.imagesearcher.data.remote.api.ApiRetrofit
import com.nadarm.imagesearcher.data.remote.api.ApiService
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofitTest {

    private lateinit var retrofit: ApiRetrofit
    private val baseUrl = "https://dapi.kakao.com/"

    @Before
    fun setUp() {
        val service = Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
        this.retrofit = ApiRetrofit(service)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun searchImage() {

    }
}