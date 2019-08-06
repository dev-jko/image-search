package com.nadarm.imagesearcher.data.remote.api

import com.nadarm.imagesearcher.data.model.response.ImageSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface ApiService {

    @GET("v2/search/image")
    fun searchImage(
        @QueryMap(encoded = true) queryMap: Map<String, String>,
        @Header("Authorization") apiKey: String
    ): Single<ImageSearchResponse>

}