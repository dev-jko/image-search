package com.nadarm.imagesearch.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.nadarm.imagesearch.domain.useCase.GetImageDocuments
import javax.inject.Inject
import javax.inject.Singleton

interface ListViewModel {

    interface Inputs {

    }

    interface Outputs {

    }

    @Singleton
    class ViewModelImpl @Inject constructor(
        private val getImageDocuments: GetImageDocuments
    ) : ViewModel(), ListViewModel.Inputs, ListViewModel.Outputs {




    }
}