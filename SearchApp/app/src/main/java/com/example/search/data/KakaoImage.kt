package com.example.search.data

data class KakaoImage(
    var title: String,
    var date: String,
    var url: String,
    var isLike: Boolean = false
)