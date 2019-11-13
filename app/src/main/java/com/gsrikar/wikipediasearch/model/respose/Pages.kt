package com.gsrikar.wikipediasearch.model.respose

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Table contains the list of pages
 */
@Entity(tableName = "pages")
data class Pages(

    @Expose
    @PrimaryKey
    @SerializedName("pageid")
    @ColumnInfo(name = "page_id")
    val pageId: Int,

    @Expose
    @SerializedName("ns")
    @ColumnInfo(name = "ns")
    val ns: Int,

    @Expose
    @SerializedName("title")
    @ColumnInfo(name = "title") val title: String?,

    @Expose
    @SerializedName("index")
    @ColumnInfo(name = "index")
    val index: Int,

    @Expose
    @Embedded
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,

    @Expose
    @Embedded
    @SerializedName("terms")
    val terms: Terms?

)