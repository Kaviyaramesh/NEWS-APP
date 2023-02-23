package com.android.aman.newsapp.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    var retrofit: Retrofit? = null
    private const val BASE_URL = "https://newsapi.org/v2/"
    // private const val BASE_URL = " https://jsonplaceholder.typicode.com/"

    val getApiClient: Retrofit?
        get() {

            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }

            return retrofit
        }
}

