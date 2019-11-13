package com.gsrikar.wikipediasearch.utils

import com.gsrikar.wikipediasearch.constants.ERROR_JSON_MESSAGE_PASCAL_CASE
import com.gsrikar.wikipediasearch.constants.ERROR_JSON_MESSAGE_SMALL_CASE
import org.json.JSONException
import org.json.JSONObject


/**
 * Parse the throwable message from the server to get the message text
 */
fun String?.getErrorMessageFromJson(): String {
    this?.let {
        return try {
            val jsonObject = JSONObject(it)
            var message = it
            if (jsonObject.has(ERROR_JSON_MESSAGE_SMALL_CASE)) {
                message = jsonObject.getString(ERROR_JSON_MESSAGE_SMALL_CASE)
            } else if (jsonObject.has(ERROR_JSON_MESSAGE_PASCAL_CASE)) {
                message = jsonObject.getString(ERROR_JSON_MESSAGE_PASCAL_CASE)
            }
            message
        } catch (e: JSONException) {
            e.printStackTrace()
            it
        }
    }
    return "Server Response is empty"
}