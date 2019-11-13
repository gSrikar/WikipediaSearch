package com.gsrikar.wikipediasearch.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gsrikar.wikipediasearch.BuildConfig
import com.gsrikar.wikipediasearch.constants.BASE_URL
import com.gsrikar.wikipediasearch.constants.REST_CONNECTION_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Object provides access to Retrofit object and its services to hit api endpoints
 */
internal object RetrofitProvider {

    /**
     * Http logging interceptor with the basic logging level
     */
    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    /**
     * Instance of the OkHttpClient interceptor
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        // Add the logging interceptor to the debug builds
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        // Set connection timeout
        builder.connectTimeout(REST_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        // Set read timeout
        builder.readTimeout(REST_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }

    /**
     * Create an instance of gson and ignores fields without expose annotation
     */
    val gson: Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    /**
     * An instance of the basic retrofit object
     */
    var retrofit: Retrofit? = null
        @Synchronized get() {
            if (field == null) {
                field = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .build()
            }
            return field
        }

}