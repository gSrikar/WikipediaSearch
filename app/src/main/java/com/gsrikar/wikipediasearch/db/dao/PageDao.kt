package com.gsrikar.wikipediasearch.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.gsrikar.wikipediasearch.model.respose.Pages


/**
 * Interface builds the queries and provides compile time validations
 */
@Dao
interface PageDao {

    /**
     * Insert the list of pages
     */
    @Insert(onConflict = IGNORE)
    fun insert(list: List<Pages>)

    /**
     * Get the results for the page title
     */
    @Query(
        """SELECT * FROM pages WHERE 
            title LIKE '%' || :title || '%' 
            OR 
            description LIKE '%' || :title || '%'
            """
    )
    fun getResults(title: String): DataSource.Factory<Int, Pages>

}