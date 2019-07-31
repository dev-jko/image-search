package com.nadarm.imagesearch.data.api

import com.nadarm.imagesearch.data.api.response.ImageSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface ApiService {

    //TODO api key를 어디에 둘지 생각해보기
    companion object {
        private const val apiKey = "91d481f38f2c2e5b0b184232a91b2740"
    }

    @Headers("Authorization: KakaoAK $apiKey")
    @GET("v2/search/image")
    fun searchImage(
        @QueryMap(encoded = true) query: Map<String, String>
    ): Single<ImageSearchResponse>

}