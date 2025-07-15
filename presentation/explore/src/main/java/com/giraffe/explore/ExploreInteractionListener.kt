package com.giraffe.explore

import com.giraffe.media.explore.entity.SearchKeyword

interface ExploreInteractionListener {
    fun onTextChange(text: String)
    fun onSearchQueryChange(query: String)
    fun onClearSearchQuery()
    fun onDeleteItemFromHistory(item: SearchKeyword)
    fun onClearHistory()
    fun onVoiceSearchClick()
    fun onClearRecentViewed()
    fun onSuggestionClick(suggestion: SearchKeyword)
    fun onTabSelected(tabIndex: Int)
    fun onViewChanged(isGrid: Boolean)
    fun onPermissionResult(granted: Boolean)
    fun onVoiceSearchFinished()

    fun onGenreSelected(genre: GenreUi)

    fun onFocusChanged(isFocused:Boolean)
}