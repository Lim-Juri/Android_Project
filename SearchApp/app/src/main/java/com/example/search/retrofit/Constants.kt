package com.example.search.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Constants {

    const val SEARCH_BASE_URL = "https://dapi.kakao.com/v2/search/image"

    const val AUTH_HEADER = "KakaoAK aa08ac34cb0de1589bd3fd9d0ebf83c2"

    const val PREFS_NAME = "IMAGE_SEARCH_PREFS"

    const val PREF_KEY = "IMAGE_SEARCH_DATA"

}