package com.giraffe.media.explore.mapper

import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.utils.getCurrentLocalDateTime
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.datasource.remote.dto.SearchKeywordDto
import kotlinx.datetime.LocalDateTime

fun SearchKeywordDto.toEntity() = SearchKeyword(
    keyword = name,
    isFromSearchHistory = false,
    lastSearchedTime = getCurrentLocalDateTime()
)

fun SearchKeywordCacheDto.toEntity() = SearchKeyword(
    keyword = keyword,
    isFromSearchHistory = true,
    lastSearchedTime = LocalDateTime.parse(lastSearchedTime)
)

fun SearchKeyword.toCacheDto() = SearchKeywordCacheDto(
    keyword = keyword,
    lastSearchedTime = lastSearchedTime.toString()
)