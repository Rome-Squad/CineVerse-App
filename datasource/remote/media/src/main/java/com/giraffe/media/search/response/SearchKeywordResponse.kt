package com.giraffe.media.search.response

import com.giraffe.media.search.datasource.remote.dto.SearchKeywordDto
import kotlinx.serialization.Serializable

@Serializable
data class SearchKeywordResponse(
    val results: List<SearchKeywordDto>,
)