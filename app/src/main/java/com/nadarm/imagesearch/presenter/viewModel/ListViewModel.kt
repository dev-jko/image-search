package com.nadarm.imagesearch.presenter.viewModel

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Singleton

interface ListViewModel {

    interface Inputs {

    }

    interface Outputs {

    }

    @Singleton
    class ViewModelImpl @Inject constructor(

    ) : ViewModel(), ListViewModel.Inputs, ListViewModel.Outputs {

    }
}