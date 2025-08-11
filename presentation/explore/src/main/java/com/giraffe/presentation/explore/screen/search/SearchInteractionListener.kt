package com.giraffe.presentation.explore.screen.search

import com.giraffe.designsystem.uimodel.Poster

interface SearchInteractionListener {
    fun onQueryChange(query: String = "")
    fun onKeywordClick(keyword: String)
    fun deleteKeyword(keyword: String)
    fun clearAllKeywords()
    fun clearAllRecentViewedPosters()
    fun onPostfixIconClick()
    fun onPermissionResult(isGranted: Boolean)
    fun onVoiceSearchFinished()
    fun onBackClick()
    fun onClickPoster(poster: Poster)
    fun onSearchClick(result: String)
    fun retry()
}