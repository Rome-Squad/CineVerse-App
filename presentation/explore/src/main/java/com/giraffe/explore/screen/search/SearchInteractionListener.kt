package com.giraffe.explore.screen.search

interface SearchInteractionListener {
    fun onQueryChange(query: String = "")
    fun onKeywordClick(keyword: String)
    fun deleteKeyword(keyword: String)
    fun clearAllKeywords()
    fun clearAllRecentViewedPosters()
    fun onPostfixIconClick()
    fun onPermissionResult(isGranted: Boolean)
    fun onVoiceSearchFinished()
}