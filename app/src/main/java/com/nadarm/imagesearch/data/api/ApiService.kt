package com.nadarm.imagesearch.data.api

import com.nadarm.imagesearch.data.api.response.ImageSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface ApiService {

    @GET("v2/search/image")
    fun searchImage(
        @QueryMap(encoded = true) query: Map<String, String>,
        @Header("Authorization") apiKey: String
    ): Single<ImageSearchResponse>

}