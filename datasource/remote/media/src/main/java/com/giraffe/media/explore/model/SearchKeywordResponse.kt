package com.giraffe.media.explore.model

import kotlinx.serialization.Serializable


@Serializable
data class SearchKeywordResponse(
    val results: List<SearchKeywordDto>,
)