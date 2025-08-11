package com.giraffe.media.explore.dummydata

import com.giraffe.media.explore.entity.SearchKeyword

val searchDummyData = SearchKeyword(
    keyword = "sci-fi",
    isFromHistory = true,
    searchedAt = System.currentTimeMillis()
)

val now = System.currentTimeMillis()

val expected = listOf(
    SearchKeyword("trending now", isFromHistory = false, searchedAt = now),
    SearchKeyword("popular", isFromHistory = true, searchedAt = now)
)