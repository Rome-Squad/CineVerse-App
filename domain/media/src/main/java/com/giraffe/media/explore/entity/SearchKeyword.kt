package com.giraffe.media.explore.entity

data class SearchKeyword(
    val keyword: String,
    val isFromHistory: Boolean,
    val searchedAt: Long
)
