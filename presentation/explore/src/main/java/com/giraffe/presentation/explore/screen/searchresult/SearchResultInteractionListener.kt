package com.giraffe.presentation.explore.screen.searchresult

import com.giraffe.presentation.explore.screen.discover.SearchTab

interface SearchResultInteractionListener {
    fun selectTap(tabIndex: Int)
    fun changeView(isGrid: Boolean)
    fun onRetryClick()
    fun onBackClick()
    fun onPosterClick(mediaId: Int, selectedTab: SearchTab)
    fun onRecordingClick()
}