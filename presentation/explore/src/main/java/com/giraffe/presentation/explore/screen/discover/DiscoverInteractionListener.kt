package com.giraffe.presentation.explore.screen.discover

import com.giraffe.presentation.explore.model.GenreUi

interface DiscoverInteractionListener {
    fun onTabSelected(tabIndex: Int)
    fun getMoviesByGenre(genreId: Int = -1)
    fun getSeriesByGenre(genreId: Int = -1)
    fun onViewChanged(isGrid: Boolean)
    fun onGenreSelected(genre: GenreUi)
    fun onPosterClick(mediaId: Int, selectedTab: SearchTab)
    fun onSearchClick()
    fun onRetryClick()
}