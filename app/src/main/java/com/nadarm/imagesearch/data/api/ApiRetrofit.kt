package com.nadarm.imagesearch.data.api

import com.nadarm.imagesearch.data.api.response.Document
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRetrofit @Inject constructor(
    private val service: ApiService
) {

    fun searchImage(query: Map<String, String>): Single<List<Document>> {
        return service.searchImage(query).map { it.documents }
    }


}