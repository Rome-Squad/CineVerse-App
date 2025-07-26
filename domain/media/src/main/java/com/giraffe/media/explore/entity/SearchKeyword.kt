package com.giraffe.media.explore.entity

data class SearchKeyword(
    val keyword: String,
    val isRecent: Boolean,
    val searchedAt: Long
)
