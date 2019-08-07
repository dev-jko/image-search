package com.nadarm.imagesearcher.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchQuery(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val query: String,
    val searchedAt: Long
)