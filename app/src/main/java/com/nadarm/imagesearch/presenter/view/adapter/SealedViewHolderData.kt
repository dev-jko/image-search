package com.nadarm.imagesearch.presenter.view.adapter

import com.nadarm.imagesearch.domain.model.ImageDocument


sealed class SealedViewHolderData {

    data class ImageItem(val imageDocument: ImageDocument, val delegate: Delegate) : SealedViewHolderData() {
        interface Delegate {
            fun imageClicked(imageDocument: ImageDocument)
        }
    }

    data class HeaderItem(val text: String) : SealedViewHolderData()
    data class FooterItem(val text: String) : SealedViewHolderData()
}


