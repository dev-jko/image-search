package com.nadarm.imagesearcher.presentation.model

import com.nadarm.imagesearcher.domain.model.ImageDocument


sealed class SealedViewHolderData {

    data class ImageItem(val imageDocument: ImageDocument, val delegate: Delegate) : SealedViewHolderData() {
        interface Delegate {
            fun imageClicked(imageDocument: ImageDocument)
        }
    }

    data class HeaderItem(val text: String) : SealedViewHolderData()

    data class FooterOneBtnItem(
        val query: String,
        val btnText: String,
        val nextPage: Int,
        val delegate: Delegate
    ) : SealedViewHolderData() {
        interface Delegate {
            fun pageChangeButtonClicked(query: String, nextPage: Int)
        }
    }

    data class FooterTwoBtnItem(
        val query: String,
        val page: Int,
        val delegate: FooterOneBtnItem.Delegate
    ) : SealedViewHolderData()
}


