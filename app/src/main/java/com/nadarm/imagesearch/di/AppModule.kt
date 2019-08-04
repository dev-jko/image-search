package com.nadarm.imagesearch.di

import android.app.Application
import dagger.Module
import dagger.Provides


@Module
object AppModule {

    lateinit var application: Application

    @JvmStatic
    @Provides
    fun provideApplication(): Application {
        return this.application
    }
}