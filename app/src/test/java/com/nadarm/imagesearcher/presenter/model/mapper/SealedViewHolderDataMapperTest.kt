package com.nadarm.imagesearcher.presenter.model.mapper

import com.nadarm.imagesearcher.domain.model.ImageDocument
import com.nadarm.imagesearcher.domain.model.QueryResponse
import com.nadarm.imagesearcher.domain.model.SearchMeta
import com.nadarm.imagesearcher.presenter.view.adapter.ImageAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class SealedViewHolderDataMapperTest {

    private lateinit var mapper: SealedViewHolderDataMapper
    private lateinit var queryResponse: QueryResponse
    private lateinit var meta: SearchMeta
    private lateinit var documents: List<ImageDocument>
    private val delegate: ImageAdapter.Delegate = mock(ImageAdapter.Delegate::class.java)

    @Before
    fun setUp() {
        this.meta = SearchMeta("query", 1, 500, 10, false)
        this.documents = listOf(
            ImageDocument("query", "thumbnailUrl0", "imageUrl0", "docUrl0", "displaySiteName0", 1, 0),
            ImageDocument("query", "thumbnailUrl1", "imageUrl1", "docUrl1", "displaySiteName1", 1, 1),
            ImageDocument("query", "thumbnailUrl2", "imageUrl2", "docUrl2", "displaySiteName2", 1, 2)
        )
        this.queryResponse = QueryResponse(this.meta, this.documents)

        mapper = SealedViewHolderDataMapper()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun mapToSealedViewHolderData() {
        val sealedDataList = this.mapper.mapToSealedViewHolderData(this.queryResponse, this.delegate)

    }

}