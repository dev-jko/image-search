package com.nadarm.imagesearcher.data.repository

import com.nadarm.imagesearcher.domain.repository.SearchQueryRepository

interface SearchQueryDataSource : SearchQueryRepository {

    interface Local : SearchQueryDataSource {

    }

}
