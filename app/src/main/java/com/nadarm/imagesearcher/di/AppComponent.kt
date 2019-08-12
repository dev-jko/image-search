package com.nadarm.imagesearcher.di

import com.nadarm.imagesearcher.data.DataBindModule
import com.nadarm.imagesearcher.presentation.view.fragment.DetailFragment
import com.nadarm.imagesearcher.presentation.view.fragment.ListFragment
import com.nadarm.imagesearcher.presentation.viewModel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataBindModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailFragment)

}