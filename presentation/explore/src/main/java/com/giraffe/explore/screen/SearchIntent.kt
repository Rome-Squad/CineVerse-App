package com.giraffe.explore.screen

import com.giraffe.explore.entity.SearchKeyword

sealed class SearchIntent {
    data class OnSearchQueryChange(val query: String) : SearchIntent()
    object OnClearSearchQuery : SearchIntent()
    data class OnDeleteItemHistory(val item: SearchKeyword) : SearchIntent()
    object OnClearHistory : SearchIntent()
    object OnClearRecentViewed : SearchIntent()
    data class OnClickItem(val suggestion: SearchKeywordResults) : SearchIntent()
    data class OnSelectedTabChanged(val tab: SearchTab) : SearchIntent()
    object onClickToggleView : SearchIntent()
}