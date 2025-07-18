package com.giraffe.media.explore.response

import com.giraffe.media.explore.datasource.remote.dto.SearchKeywordDto
import kotlinx.serialization.Serializable

@Serializable
data class SearchKeywordResponse(
    val results: List<SearchKeywordDto>,
)