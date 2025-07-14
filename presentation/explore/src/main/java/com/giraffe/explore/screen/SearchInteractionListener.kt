package com.giraffe.explore.screen

import com.giraffe.explore.entity.SearchKeyword

interface SearchInteractionListener {
    fun onSearchQueryChange(query: String)
    fun onClearSearchQuery()
    fun onDeleteItemFromHistory(item: SearchKeyword)
    fun onClearHistory()
    fun onVoiceSearchClick()
    fun onClearRecentViewed()
    fun onSuggestionClick(suggestion: SearchKeyword)
    fun onTabSelected(keyword: SearchKeyword, tab: SearchTab)
    fun onToggleViewClick()
    fun onPermissionResult(granted: Boolean)
    fun onVoiceSearchFinished()
}