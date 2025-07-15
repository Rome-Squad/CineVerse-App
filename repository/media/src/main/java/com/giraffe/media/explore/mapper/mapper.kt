package com.giraffe.media.explore.mapper

import com.giraffe.media.explore.model.SearchKeywordCacheDto
import com.giraffe.media.explore.utils.getCurrentLocalDateTime
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.model.SearchKeywordDto
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

fun  List<SearchKeywordCacheDto>.toEntity(): List<SearchKeyword> {
    return this.map {
        it.toEntity()
    }
}