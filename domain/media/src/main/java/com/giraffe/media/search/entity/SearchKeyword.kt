package com.giraffe.media.search.entity

data class SearchKeyword(
    val keyword: String,
    val isFromHistory: Boolean,
    val searchedAt: Long
)
