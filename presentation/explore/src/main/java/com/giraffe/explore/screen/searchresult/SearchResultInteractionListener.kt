package com.giraffe.explore.screen.searchresult

interface SearchResultInteractionListener {
    fun selectTap(tabIndex: Int)
    fun changeView(isGrid: Boolean)
    fun retry()
}