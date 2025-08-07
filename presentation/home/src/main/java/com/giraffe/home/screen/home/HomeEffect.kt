package com.giraffe.home.screen.home

import androidx.annotation.StringRes


sealed interface HomeEffect {
    data class NavigateToMovieDetails(val movieId: Int) : HomeEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : HomeEffect
    data class NavigateToRecentlyReleasedList(val sectionTitle: String, val sectionType: String) : HomeEffect
    data object NavigateToTopRatedTvShows : HomeEffect
    data object NavigateToUpcomingMovies : HomeEffect
    data object NavigateToRecentlyViewed : HomeEffect
    data class NavigateToRecommendedList(val sectionTitle: String, val sectionType: String) : HomeEffect
    data class NavigateToFeaturedCollection(val collectionId: Int, val collectionTitle: String) : HomeEffect
    data class ShowError(@param:StringRes val messageRes: Int) : HomeEffect
}