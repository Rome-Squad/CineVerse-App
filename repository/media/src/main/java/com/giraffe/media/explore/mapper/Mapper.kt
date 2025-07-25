package com.giraffe.media.explore.mapper

import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.explore.datasource.remote.dto.SearchKeywordDto
import com.giraffe.media.explore.entity.SearchKeyword

fun SearchKeywordDto.toEntity() = SearchKeyword(
    keyword = name,
    isRecent = false,
    searchedAt = System.currentTimeMillis()
)

fun SearchKeywordCacheDto.toEntity() = SearchKeyword(
    keyword = keyword,
    isRecent = true,
    searchedAt = searchedAt
)