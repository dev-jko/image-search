package com.nadarm.imagesearch

import android.app.Application

class AndroidApplication : Application() {

    private val appComponent: AppComponent = DaggerAppComponent.builder()
        .build()

    fun getAppComponent(): AppComponent = this.appComponent
}