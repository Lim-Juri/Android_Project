package com.example.search.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val apiService: NetWorkInterface
        get() = instance.create(NetWorkInterface::class.java)

    private val instance: Retrofit
        private get() {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(Constants.SEARCH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}