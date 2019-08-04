package com.nadarm.imagesearch.data.repository

import com.nadarm.imagesearch.domain.repository.SearchQueryRepository

interface SearchQueryDataSource : SearchQueryRepository {

    interface Local : SearchQueryDataSource {

    }

}
