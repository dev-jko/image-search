package com.nadarm.imagesearch.presenter.viewModel

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Singleton

interface SearchViewModel {

    interface Inputs {

    }

    interface Outputs {

    }


    @Singleton
    class ViewModelImpl @Inject constructor(

    ) : ViewModel(), Inputs, Outputs {


    }
}