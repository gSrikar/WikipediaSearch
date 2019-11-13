package com.gsrikar.wikipediasearch.model.respose

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Query (

	@Expose
	@SerializedName("pages")
	val pages : List<Pages>

)