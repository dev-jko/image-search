package com.nadarm.imagesearch

import com.nadarm.imagesearch.data.DataBindModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataBindModule::class])
interface AppComponent {

}