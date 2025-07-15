package com.giraffe.media.explore.usecase

data class ExploreUseCases(
    val getSearchKeywords: GetSearchKeywordsUseCase,
    val insertSearchKeyword: InsertSearchKeywordUseCase,
    val deleteSearchKeyword: DeleteSearchKeywordUseCase,
    val clearSearchHistory: ClearSearchHistoryUseCase
)
