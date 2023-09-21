package com.example.search.retrofit

import com.example.search.data.ImageSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetWorkInterface {
    @GET("v2/search/image")
    fun searchImage(
        @Header("Authorization") apiKey: String?,
        @Query("query") query: String?,
        @Query("sort") sort: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ImageSearchResponse?>?
}