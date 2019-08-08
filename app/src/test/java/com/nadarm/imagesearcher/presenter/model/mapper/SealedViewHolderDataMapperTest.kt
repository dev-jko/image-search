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
        mapper = SealedViewHolderDataMapper()


        meta = SearchMeta()

        queryResponse =
    }

    @After
    fun tearDown() {

    }

    @Test
    fun mapToSealedViewHolderData() {

        val
        val meta = SearchMeta()
        val queryResponse = QueryResponse()

    }


}