package com.nadarm.imagesearch.data.api

import com.nadarm.imagesearch.data.api.response.Document
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRetrofit @Inject constructor(
    private val service: ApiService
) {

    private val baseUrl = "https://dapi.kakao.com/"

    fun searchImage(query: String): Single<List<Document>> {
        return service.searchImage(query).map { it.documents }
    }


}