package com.nadarm.imagesearch.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nadarm.imagesearch.data.model.SearchQuery

@Database(entities = [SearchQuery::class], version = 4)
abstract class SearchQueryDatabase : RoomDatabase() {

    abstract fun getDao(): SearchQueryDao

    companion object {
        private var INSTANCE: SearchQueryDatabase? = null

        fun getInstance(application: Application): SearchQueryDatabase {
            return INSTANCE ?: synchronized(this) {
                buildDatabase(application).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(application: Application): SearchQueryDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                SearchQueryDatabase::class.java,
                "searchQuery.db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

}