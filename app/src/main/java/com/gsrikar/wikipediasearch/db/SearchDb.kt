package com.gsrikar.wikipediasearch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gsrikar.wikipediasearch.db.converters.JsonConverter
import com.gsrikar.wikipediasearch.db.dao.PageDao
import com.gsrikar.wikipediasearch.model.respose.Pages


/**
 * Default Database for the application
 */
@Database(
    entities = [Pages::class],
    version = 6
)
@TypeConverters(JsonConverter::class)
abstract class SearchDb : RoomDatabase() {

    abstract fun pageDao(): PageDao
}