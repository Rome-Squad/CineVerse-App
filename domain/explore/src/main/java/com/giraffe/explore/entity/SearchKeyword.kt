package com.giraffe.explore.entity

import kotlinx.datetime.LocalDateTime

data class SearchKeyword(
    val keyword: String,
    val lastSearchedTime: LocalDateTime
)
