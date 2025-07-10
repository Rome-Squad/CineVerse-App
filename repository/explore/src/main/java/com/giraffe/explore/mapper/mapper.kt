package com.giraffe.explore.mapper

import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.model.SearchKeywordCacheDto
import com.giraffe.explore.model.SearchKeywordDto
import com.giraffe.explore.utils.getCurrentLocalDateTime
import kotlinx.datetime.LocalDateTime

fun SearchKeywordDto.toEntity(): SearchKeyword {
    return SearchKeyword(
        keyword = this.name,
        isFromSearchHistory = false,
        lastSearchedTime = getCurrentLocalDateTime() // remote doesn’t send time
    )
}

fun SearchKeywordCacheDto.toEntity(): SearchKeyword {
    return SearchKeyword(
        keyword = this.keyword,
        isFromSearchHistory = true,
        lastSearchedTime = LocalDateTime.parse(this.lastSearchedTime)
    )
}

fun SearchKeyword.toCacheDto(): SearchKeywordCacheDto {
    return SearchKeywordCacheDto(
        keyword = this.keyword,
        lastSearchedTime = this.lastSearchedTime.toString()
    )
}
