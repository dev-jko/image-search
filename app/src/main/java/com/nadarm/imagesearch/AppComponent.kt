package com.nadarm.imagesearch

import com.nadarm.imagesearch.data.DataBindModule
import com.nadarm.imagesearch.presenter.view.fragment.DetailFragment
import com.nadarm.imagesearch.presenter.view.fragment.ListFragment
import com.nadarm.imagesearch.presenter.viewModel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataBindModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailFragment)

}