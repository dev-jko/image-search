package com.nadarm.imagesearcher.presentation.model.mapper

import com.nadarm.imagesearcher.domain.model.ImageDocument
import com.nadarm.imagesearcher.domain.model.QueryResponse
import com.nadarm.imagesearcher.domain.model.SearchMeta
import com.nadarm.imagesearcher.presentation.model.SealedViewHolderData
import com.nadarm.imagesearcher.presentation.view.adapter.ImageAdapter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class SealedViewHolderDataMapperTest {

    private lateinit var mapper: SealedViewHolderDataMapper
    private var queryResponse: QueryResponse? = null
    private val delegate: ImageAdapter.Delegate = mock(ImageAdapter.Delegate::class.java)


    @Before
    fun setUp() {
        this.queryResponse = null
        this.mapper = SealedViewHolderDataMapper()
    }


    @Test
    fun `test mapToSealedViewHolderData with first page and not last`() {
        this.queryResponse = createQuerySearch(1, false)
        val sealedDataList = this.mapper.mapToSealedViewHolderData(this.queryResponse!!, this.delegate)
        assertData(this.queryResponse!!, sealedDataList)
    }

    @Test
    fun `test mapToSealedViewHolderData with not first page and not last`() {
        this.queryResponse = createQuerySearch(2, false)
        val sealedDataList = this.mapper.mapToSealedViewHolderData(this.queryResponse!!, this.delegate)
        assertData(this.queryResponse!!, sealedDataList)
    }

    @Test
    fun `test mapToSealedViewHolderData with first page and last`() {
        this.queryResponse = createQuerySearch(1, true)
        val sealedDataList = this.mapper.mapToSealedViewHolderData(this.queryResponse!!, this.delegate)
        assertData(this.queryResponse!!, sealedDataList)
    }

    @Test
    fun `test mapToSealedViewHolderData with not first page and last`() {
        this.queryResponse = createQuerySearch(2, true)
        val sealedDataList = this.mapper.mapToSealedViewHolderData(this.queryResponse!!, this.delegate)
        assertData(this.queryResponse!!, sealedDataList)
    }

    private fun createQuerySearch(page: Int, isEnd: Boolean): QueryResponse {
        val query = "query"
        val meta = SearchMeta(query, page, 500, 10, isEnd)
        val documents = listOf(
            ImageDocument(query, "thumbnailUrl0", "imageUrl0", "docUrl0", "displaySiteName0", page, 0),
            ImageDocument(query, "thumbnailUrl1", "imageUrl1", "docUrl1", "displaySiteName1", page, 1),
            ImageDocument(query, "thumbnailUrl2", "imageUrl2", "docUrl2", "displaySiteName2", page, 2)
        )
        return QueryResponse(meta, documents)
    }

    private fun assertData(queryResponse: QueryResponse, dataList: List<SealedViewHolderData>) {
        assertHeader(queryResponse, dataList)
        assertImages(queryResponse, dataList)
        assertFooter(queryResponse, dataList)
    }

    private fun assertFooter(queryResponse: QueryResponse, dataList: List<SealedViewHolderData>) {
        val page = queryResponse.meta.page
        val isEnd = queryResponse.meta.isEnd

        when (val footer = dataList.last()) {
            is SealedViewHolderData.FooterOneBtnItem -> {
                if (footer.btnText == "다음") assertTrue(page == 1 && !isEnd)
                else assertTrue(page > 1 && isEnd)
            }
            is SealedViewHolderData.FooterTwoBtnItem -> assertTrue(page > 1 && !isEnd)
            else -> assertEquals(queryResponse.documents.size + 1, dataList.size)
        }
    }

    private fun assertImages(queryResponse: QueryResponse, dataList: List<SealedViewHolderData>) {
        val isNoFooter = queryResponse.meta.page == 1 && queryResponse.meta.isEnd
        val lastImageIndex = if (isNoFooter) dataList.size - 1 else dataList.size - 2
        val images = dataList.subList(1, lastImageIndex)
        images.forEachIndexed { index, sealedViewHolderData ->
            val expected = queryResponse.documents[index]
            val actual = (sealedViewHolderData as SealedViewHolderData.ImageItem).imageDocument
            assertEquals(expected, actual)
        }
    }

    private fun assertHeader(queryResponse: QueryResponse, dataList: List<SealedViewHolderData>) {
        val expected = "${queryResponse.meta.query} 검색결과 : ${queryResponse.meta.totalCount}개"
        val actual = (dataList[0] as SealedViewHolderData.HeaderItem).text
        assertEquals(expected, actual)
    }


}