package com.gsrikar.wikipediasearch.model.respose

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "terms")
data class Terms(

    @Expose
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: List<String>?
)