package com.example.search_key

// 앱 전체에서 사용되는 상수들을 저장하는 객체

object Constants {

    // Kakao Image Search API 기본 URL
    const val BASE_URL = "https://dapi.kakao.com"

    // Kakao API를 사용하기 위한 인증 헤더
    const val AUTH_HEADER = "KakaoAK aa08ac34cb0de1589bd3fd9d0ebf83c2"

    // 앱의 Shared Preferences 파일 이름
    const val PREFS_NAME = "imagesearch.prefs"

    // 마지막 검색어를 저장하기 위한 키 값
    const val PREF_KEY = "IMAGE_SEARCH_PREF"
}

