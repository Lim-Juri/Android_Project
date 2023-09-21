package com.example.search_key.api

import com.example.search_key.model.ImageModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Retrofit_interface {
    @GET("v2/search/image")
    fun image_search(
        @Header("Authorization") apiKey: String?,
        @Query("query") query: String?,
        @Query("sort") sort: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<ImageModel?>?
    // Call과 Response의 차이
    // Call은 서버에 대한 요청과 응답 결과에 대한 반환을 한 번에 수행하고,
    // Response는 요청 이후 응답 결과에 대한 반환만 수행한다.
}