package com.giraffe.media.search.mapper

import com.giraffe.media.search.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.search.datasource.remote.dto.SearchKeywordDto
import com.giraffe.media.search.entity.SearchKeyword

fun SearchKeywordDto.toEntity() = SearchKeyword(
    keyword = name,
    isFromHistory = false,
    searchedAt = System.currentTimeMillis()
)

fun SearchKeywordCacheDto.toEntity() = SearchKeyword(
    keyword = keyword,
    isFromHistory = true,
    searchedAt = searchedAt
)