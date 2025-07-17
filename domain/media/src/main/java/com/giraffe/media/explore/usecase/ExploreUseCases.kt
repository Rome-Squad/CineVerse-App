package com.giraffe.media.explore.usecase

data class ExploreUseCases(
    val getSearchKeywords: GetSearchKeywordsUseCase,
    val insertSearchKeyword: InsertSearchKeywordUseCase,
    val deleteSearchKeyword: DeleteKeywordUseCase,
    val clearSearchHistory: ClearSearchHistoryUseCase
)
