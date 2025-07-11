package com.giraffe.explore

sealed class SearchIntent {
    data class OnSearchQueryChange(val query: String) : SearchIntent()
    object OnClearSearchQuery : SearchIntent()
    data class OnClearItemHistory(val item: String) : SearchIntent()
    object OnClearHistory : SearchIntent()
    object OnClearRecentViewed : SearchIntent()
    object OnVoiceSearchClick : SearchIntent()
    data class OnVoiceSearchResult(val result: String) : SearchIntent()
    data class OnChooseSuggestion(val suggestion: String) : SearchIntent()
    data class OnSelectedTabChanged(val tab: SearchTab) : SearchIntent()
    object onClickToggleView : SearchIntent()
}