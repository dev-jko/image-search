package com.nadarm.imagesearch.presenter.model.mapper

import com.nadarm.imagesearch.domain.model.QueryResponse
import com.nadarm.imagesearch.presenter.model.SealedViewHolderData
import com.nadarm.imagesearch.presenter.view.adapter.ImageAdapter
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SealedViewHolderDataMapper @Inject constructor() {

    fun mapToSealedViewHolderData(
        queryResponse: QueryResponse,
        delegate: ImageAdapter.Delegate
    ): List<SealedViewHolderData> {
        val itemList: MutableList<SealedViewHolderData> = ArrayList()
        val query = queryResponse.meta.query
        val page = queryResponse.meta.page
        val isEnd = queryResponse.meta.isEnd

        val headerText: String = "$query 검색결과 : ${queryResponse.meta.totalCount}개"
        itemList.add(SealedViewHolderData.HeaderItem(headerText))

        val items = queryResponse.documents.map {
            SealedViewHolderData.ImageItem(
                it,
                delegate
            )
        }
        itemList.addAll(items)

        if (page == 1 && !isEnd) {
            itemList.add(SealedViewHolderData.FooterOneBtnItem(query, "다음", page + 1, delegate))
        } else if (page > 1 && isEnd) {
            itemList.add(SealedViewHolderData.FooterOneBtnItem(query, "이전", page - 1, delegate))
        } else if (page > 1 && !isEnd) {
            itemList.add(SealedViewHolderData.FooterTwoBtnItem(query, page, delegate))
        }

        return itemList
    }


}