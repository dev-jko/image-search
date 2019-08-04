package com.nadarm.imagesearch.di

import android.app.Application


class AndroidApplication : Application() {

    private val appComponent: AppComponent = DaggerAppComponent.builder()
        .build()

    fun getAppComponent(): AppComponent = this.appComponent
}