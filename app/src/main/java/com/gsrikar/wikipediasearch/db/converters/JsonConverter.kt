package com.gsrikar.wikipediasearch.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson


/**
 * Converts json to string and string to json
 */
class JsonConverter {

    @TypeConverter
    fun listToJson(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        return objects.toList()
    }
}