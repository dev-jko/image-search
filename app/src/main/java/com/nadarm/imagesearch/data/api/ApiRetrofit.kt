package com.nadarm.imagesearch.data.api

import com.nadarm.imagesearch.BuildConfig
import com.nadarm.imagesearch.data.api.response.Document
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRetrofit @Inject constructor(
    private val service: ApiService
) {
    private val apiKey = BuildConfig.ApiKey

    fun searchImage(query: Map<String, String>): Single<List<Document>> {
        return service.searchImage(query, apiKey).map { it.documents }
    }

}