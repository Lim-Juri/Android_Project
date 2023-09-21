package com.example.search.data

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("is_end")
    val isEnd: Boolean?,

    @SerializedName("pageable_count")
    val pageableCount: Int,

    @SerializedName("total_count")
    val totalCount: Int
)
