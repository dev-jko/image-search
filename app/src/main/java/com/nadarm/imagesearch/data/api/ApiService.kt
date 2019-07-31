package com.nadarm.imagesearch.data.api

import com.nadarm.imagesearch.data.api.response.ImageSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    //TODO header 인증 바꾸기
    @Headers("Authorization: KakaoAK")
    @GET("v2/search/image")
    fun searchImage(@Query("query") query: String): Single<ImageSearchResponse>

}