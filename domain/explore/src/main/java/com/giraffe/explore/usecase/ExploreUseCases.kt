package com.giraffe.explore.usecase

data class ExploreUseCases(
    val getSearchKeywords: GetSearchKeywordsUseCase,
    val insertSearchKeyword: InsertSearchKeywordUseCase,
    val deleteSearchKeyword: DeleteSearchKeywordUseCase,
    val clearSearchHistory: ClearSearchHistoryUseCase
)
