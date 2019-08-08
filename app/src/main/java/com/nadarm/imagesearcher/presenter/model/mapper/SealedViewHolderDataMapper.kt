package com.nadarm.imagesearcher.presenter.model.mapper

import com.nadarm.imagesearcher.domain.model.QueryResponse
import com.nadarm.imagesearcher.presenter.model.SealedViewHolderData
import com.nadarm.imagesearcher.presenter.view.adapter.ImageAdapter
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SealedViewHolderDataMapper @Inject constructor() {

    fun mapToSealedViewHolderData(
        queryResponse: QueryResponse,
        delegate: ImageAdapter.Delegate
    ): List<SealedViewHolderData> {
        val query = queryResponse.meta.query
        val page = queryResponse.meta.page
        val isEnd = queryResponse.meta.isEnd

        val itemList: MutableList<SealedViewHolderData> = ArrayList()
        itemList.add(makeHeader(query, queryResponse))
        itemList.addAll(makeImages(queryResponse, delegate))
        val footer = makeFooter(query, page, isEnd, delegate)
        if (footer != null) {
            itemList.add(footer)
        }
        return itemList
    }

    private fun makeFooter(
        query: String,
        page: Int,
        isEnd: Boolean,
        delegate: ImageAdapter.Delegate
    ): SealedViewHolderData? {
        return when {
            page == 1 && !isEnd -> SealedViewHolderData.FooterOneBtnItem(query, "다음", page + 1, delegate)
            page > 1 && isEnd -> SealedViewHolderData.FooterOneBtnItem(query, "이전", page - 1, delegate)
            page > 1 && !isEnd -> SealedViewHolderData.FooterTwoBtnItem(query, page, delegate)
            else -> null
        }
    }

    private fun makeImages(
        queryResponse: QueryResponse,
        delegate: ImageAdapter.Delegate
    ): List<SealedViewHolderData.ImageItem> {
        return queryResponse.documents.map {
            SealedViewHolderData.ImageItem(it, delegate)
        }
    }

    private fun makeHeader(
        query: String,
        queryResponse: QueryResponse
    ): SealedViewHolderData.HeaderItem {
        val headerText: String = "$query 검색결과 : ${queryResponse.meta.totalCount}개"
        return SealedViewHolderData.HeaderItem(headerText)
    }


}