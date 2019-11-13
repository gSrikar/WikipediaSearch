package com.gsrikar.wikipediasearch.model.respose

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Continue(

    @Expose
    @SerializedName("gpsoffset")
    val gpsOffset: Int,

    @Expose
    @SerializedName("continue")
    val shouldContinue: String

)