package com.nadarm.imagesearch.data

import android.app.Application
import com.nadarm.imagesearch.data.cache.ImageDocumentCacheDataSource
import com.nadarm.imagesearch.data.cache.RecentlyViewedCacheDataSource
import com.nadarm.imagesearch.data.local.SearchQueryDao
import com.nadarm.imagesearch.data.local.SearchQueryDatabase
import com.nadarm.imagesearch.data.local.SearchQueryLocalDataSource
import com.nadarm.imagesearch.data.model.mapper.ImageDocumentMapper
import com.nadarm.imagesearch.data.remote.ImageDocumentRemoteDataSource
import com.nadarm.imagesearch.data.remote.api.ApiService
import com.nadarm.imagesearch.data.repository.*
import com.nadarm.imagesearch.domain.repository.ImageDocumentRepository
import com.nadarm.imagesearch.domain.repository.RecentlyViewedRepository
import com.nadarm.imagesearch.domain.repository.SearchQueryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [DataProvideModule::class])
interface DataBindModule {

    @Singleton
    @Binds
    fun bindImageDocumentRemoteDataSource(dataSource: ImageDocumentRemoteDataSource): ImageDocumentDataSource.Remote

    @Singleton
    @Binds
    fun bindImageDocumentCacheDataSource(dataSource: ImageDocumentCacheDataSource): ImageDocumentDataSource.Cache

    @Singleton
    @Binds
    fun bindImageRepository(repository: ImageDocumentDataRepository): ImageDocumentRepository

    @Singleton
    @Binds
    fun bindRecentlyViewedCacheDataSource(dataSource: RecentlyViewedCacheDataSource): RecentlyViewedDataSource.Cache

    @Singleton
    @Binds
    fun bindRecentlyViewedRepository(repository: RecentlyViewedDataRepository): RecentlyViewedRepository

    @Singleton
    @Binds
    fun bindSearchQueryRepository(repository: SearchQueryDataRepository): SearchQueryRepository

    @Singleton
    @Binds
    fun bindSearchQueryLocalDataSource(dataSource: SearchQueryLocalDataSource): SearchQueryDataSource.Local
}

@Module
object DataProvideModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val baseUrl = "https://dapi.kakao.com/"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @JvmStatic
    @Provides
    fun provideImageDocumentMapper(): ImageDocumentMapper = ImageDocumentMapper

    @JvmStatic
    @Singleton
    @Provides
    fun provideSearchQueryDao(application: Application): SearchQueryDao {
        return SearchQueryDatabase.getInstance(application).getDao()
    }


}