package com.gsrikar.wikipediasearch.model.respose

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SearchResponse(

    @Expose
    @SerializedName("batchcomplete")
    val batchComplete: Boolean,

    @Expose
    @SerializedName("continue")
    val shouldContinue: Continue,

    @Expose
    @SerializedName("query")
    val query: Query

)