package com.gsrikar.wikipediasearch.utils

import okhttp3.ResponseBody
import java.io.IOException


/**
 * Contains common used functions
 */
object CommonUtils {

    /**
     * Error message build based on the error body and the error message
     */
    fun errorMessage(errorBody: ResponseBody?, message: String): String {
        val msg = try {
            errorBody?.string()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            null
        }
        return if (msg.isNullOrEmpty()) {
            message
        } else {
            msg
        }
    }
}