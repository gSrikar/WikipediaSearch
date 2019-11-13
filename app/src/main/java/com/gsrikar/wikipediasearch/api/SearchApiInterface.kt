package com.gsrikar.wikipediasearch.api

import com.gsrikar.wikipediasearch.model.respose.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 * Retrofit interface to generate the search query
 */
interface SearchApiInterface {

    /**
     * Search for the results
     */
    @GET("w/api.php")
    fun search(@QueryMap map: Map<String, String>): Call<SearchResponse>
}