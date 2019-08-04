package com.nadarm.imagesearch.domain.repository

import android.database.Cursor
import io.reactivex.Completable

interface SearchQueryRepository {

    fun getSuggestions(query: String): Cursor

    fun addSearchedQuery(query: String): Completable

}