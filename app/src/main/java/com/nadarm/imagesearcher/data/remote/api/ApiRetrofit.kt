package com.nadarm.imagesearcher.data.remote.api

import com.nadarm.imagesearcher.BuildConfig
import com.nadarm.imagesearcher.data.model.response.ImageSearchResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRetrofit @Inject constructor(
    private val service: ApiService
) {
    private val apiKey = BuildConfig.ApiKey

    fun searchImage(query: String, page: Int): Single<ImageSearchResponse> {
        val queryMap: Map<String, String> = mapOf<String, String>(
            "query" to query,
            "page" to page.toString()
        )
        return service.searchImage(queryMap, apiKey)
    }

}