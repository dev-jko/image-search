package com.nadarm.imagesearch.domain.useCase

import android.database.Cursor
import com.nadarm.imagesearch.domain.repository.SearchQueryRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSuggestions @Inject constructor(
    private val repository: SearchQueryRepository
) : SingleUseCase1<String, Cursor> {

    override fun execute(query: String): Single<Cursor> {
        return Single.just(this.repository.getSuggestions(query))
    }
}