package com.nadarm.imagesearch

import com.nadarm.imagesearch.data.DataBindModule
import dagger.Component

@Component(modules = [AppModule::class, DataBindModule::class])
interface AppComponent {

}