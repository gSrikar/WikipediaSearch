package com.gsrikar.wikipediasearch.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import com.gsrikar.wikipediasearch.BuildConfig
import com.gsrikar.wikipediasearch.constants.DATABASE_NAME_SEARCH
import com.gsrikar.wikipediasearch.db.SearchDb
import com.gsrikar.wikipediasearch.utils.AppExecutors


/**
 * Entry point to the Application
 */
class WikiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        if (BuildConfig.DEBUG) {
            // Initialize the library to monitor the database
            Stetho.initializeWithDefaults(this)
        }

        // Initialize the fresco library
        Fresco.initialize(this)
    }

    companion object {
        /**
         * Application context
         */
        private lateinit var appContext: Context

        /**
         * @return instance of the database
         */
        fun getSearchDb(): SearchDb {
            return Room.databaseBuilder(
                appContext,
                SearchDb::class.java,
                DATABASE_NAME_SEARCH
            ).fallbackToDestructiveMigration()
                .build()
        }

        // Instance of app executors
        val appExecutors = AppExecutors()
    }
}