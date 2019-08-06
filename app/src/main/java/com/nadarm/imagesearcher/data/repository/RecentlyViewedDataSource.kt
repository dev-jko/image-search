package com.nadarm.imagesearcher.data.repository

import com.nadarm.imagesearcher.domain.repository.RecentlyViewedRepository

interface RecentlyViewedDataSource : RecentlyViewedRepository {

    interface Cache : RecentlyViewedDataSource

}