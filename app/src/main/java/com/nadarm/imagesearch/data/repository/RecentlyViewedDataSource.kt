package com.nadarm.imagesearch.data.repository

import com.nadarm.imagesearch.domain.repository.RecentlyViewedRepository

interface RecentlyViewedDataSource : RecentlyViewedRepository {

    interface Cache : RecentlyViewedDataSource

}