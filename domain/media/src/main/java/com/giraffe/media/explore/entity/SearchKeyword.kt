package com.giraffe.media.explore.entity

import kotlinx.datetime.LocalDateTime

data class SearchKeyword(
    val keyword: String,
    val isFromSearchHistory: Boolean,
    val lastSearchedTime: LocalDateTime
)
