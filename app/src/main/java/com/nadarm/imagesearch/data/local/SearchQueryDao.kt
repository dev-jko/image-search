package com.nadarm.imagesearch.data.local

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nadarm.imagesearch.data.model.SearchQuery
import io.reactivex.Completable

@Dao
abstract class SearchQueryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertQuery(query: SearchQuery): Completable

    @Query("SELECT _id FROM searchQuery WHERE _id LIKE :text ORDER BY searchedAt DESC LIMIT 4")
    protected abstract fun getText(text: String): Cursor

    fun getQuery(query: String): Cursor {
        val newQuery: String = "%$query%"
        return getText(newQuery)
    }

}