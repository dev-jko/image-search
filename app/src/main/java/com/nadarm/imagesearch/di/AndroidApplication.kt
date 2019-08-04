package com.nadarm.imagesearch.di

import android.app.Application


class AndroidApplication : Application() {


    private val appComponent: AppComponent

    init {
        AppModule.application = this
        this.appComponent = DaggerAppComponent.builder()
            .build()
    }

    fun getAppComponent(): AppComponent = this.appComponent
}