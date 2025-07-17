package com.giraffe.explore.screen.discover

interface DiscoverInteractionListener {
    fun onTabSelected(tabIndex: Int)
    fun getMoviesByGenre(genreId: Int = -1)
    fun getSeriesByGenre(genreId: Int = -1)
    fun onViewChanged(isGrid: Boolean)
    fun onGenreSelected(genre: GenreUi)
}