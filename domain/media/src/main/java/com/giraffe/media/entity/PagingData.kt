package com.giraffe.media.entity

data class PagingData<T>(
    val data: T,
    val totalResults: Int
)
