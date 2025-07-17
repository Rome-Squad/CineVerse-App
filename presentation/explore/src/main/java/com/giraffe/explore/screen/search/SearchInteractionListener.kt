package com.giraffe.explore.screen.search

interface SearchInteractionListener {
    fun onQueryChange(query: String = "")
    fun onKeywordClick(keyword: String)
    fun deleteKeyword(keyword: String)
    fun clearAllKeywords()
    fun onPostfixIconClick()
}
/*fun onClearSearchQuery()
    fun onDeleteItemFromHistory(item: SearchKeyword)
    fun onClearHistory()
    fun onVoiceSearchClick()
    fun onClearRecentViewed()
    fun onSuggestionClick(suggestion: SearchKeyword)

    fun onPermissionResult(granted: Boolean)
    fun onVoiceSearchFinished()


    fun onFocusChanged(isFocused: Boolean)
    fun onSearchModeChanged(isSearchMode: Boolean)*/