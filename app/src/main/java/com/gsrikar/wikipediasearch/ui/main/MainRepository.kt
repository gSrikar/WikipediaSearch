package com.gsrikar.wikipediasearch.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gsrikar.wikipediasearch.api.SearchApiInterface
import com.gsrikar.wikipediasearch.app.WikiApplication.Companion.appExecutors
import com.gsrikar.wikipediasearch.db.dao.PageDao
import com.gsrikar.wikipediasearch.model.respose.Pages
import com.gsrikar.wikipediasearch.model.respose.SearchResponse
import com.gsrikar.wikipediasearch.network.Resource
import com.gsrikar.wikipediasearch.utils.CommonUtils.errorMessage
import com.gsrikar.wikipediasearch.utils.getErrorMessageFromJson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Retrieves the data requested by [MainViewModel] from the server or the local database
 */
class MainRepository(
    private val searchResponse: SearchApiInterface?,
    private val pageDao: PageDao
) {

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()

    /**
     * Mediator Live data listens to the changes in the [Pages] database
     */
    val pagesMutableLiveData = MediatorLiveData<Resource<PagedList<Pages>>>()

    /**
     * Search for the item
     */
    fun search(query: String) {
        // Set the status to loading
        pagesMutableLiveData.value = Resource.loading(null)
        // Make an api call
        searchResponse?.search(getSearchMap(query))?.enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                parseErrorResponse(t, query)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                parseSearchResponse(response, query)
            }
        })
    }

    private fun parseErrorResponse(t: Throwable, query: String) {
        // Post the error message
        readPagesInDb(t.message.getErrorMessageFromJson(), query)
    }

    private fun parseSearchResponse(
        response: Response<SearchResponse>,
        query: String
    ) {
        if (response.isSuccessful) {
            saveSearchResponse(response, query)
        } else {
            // Check the spends in the db and send the error message
            readPagesInDb(
                errorMessage(
                    response.errorBody(),
                    response.message()
                ).getErrorMessageFromJson(),
                query
            )
        }
    }

    /**
     * Save the data in the [Pages] table
     */
    private fun saveSearchResponse(
        response: Response<SearchResponse>,
        title: String
    ) {
        val searchResponse = response.body()
        // Insert the list of pages
        appExecutors.diskIO().execute {
            searchResponse?.query?.pages?.let { pageDao.insert(it) }
            // Read the data from the local database
            appExecutors.mainThread().execute {
                readPagesInDb(null, title)
            }
        }
    }

    /**
     * Read the the pages information for the query in the database
     */
    private fun readPagesInDb(error: String?, title: String) {
        pagesMutableLiveData.addSource(
            LivePagedListBuilder(
                pageDao.getResults(title),
                config
            ).build()
        ) { list ->
            error?.let {
                // Show the data with the error
                pagesMutableLiveData.value = Resource.error(it, list)
            } ?: kotlin.run {
                // Show the data as success
                pagesMutableLiveData.value = Resource.success(list)
            }
        }
    }

    /**
     * @return Search query parameters
     */
    private fun getSearchMap(query: String): Map<String, String> {
        return hashMapOf<String, String>().apply {
            this["action"] = "query"
            this["format"] = "json"
            this["prop"] = "pageimages|pageterms"
            this["generator"] = "prefixsearch"
            this["redirects"] = "10"
            this["formatversion"] = "2"
            this["piprop"] = "thumbnail"
            this["pithumbsize"] = "400"
            this["pilimit"] = "20"
            this["wbptterms"] = "description"
            this["gpssearch"] = query
        }
    }
}