package com.nadarm.imagesearch.data

import android.app.Application
import com.nadarm.imagesearch.data.api.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [DataProvideModule::class])
interface DataBindModule {

}

@Module
object DataProvideModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiService(application: Application): ApiService {
        val baseUrl = "https://dapi.kakao.com/"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}