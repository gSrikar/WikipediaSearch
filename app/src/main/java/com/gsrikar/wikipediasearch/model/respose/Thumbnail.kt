package com.gsrikar.wikipediasearch.model.respose


import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "thumbnail")
data class Thumbnail(

    @Expose
    @ColumnInfo(name = "source")
    @SerializedName("source")
    val source: String?,

    @Expose
    @ColumnInfo(name = "width")
    @SerializedName("width")
    val width: Int,

    @Expose
    @ColumnInfo(name = "height")
    @SerializedName("height")
    val height: Int

)