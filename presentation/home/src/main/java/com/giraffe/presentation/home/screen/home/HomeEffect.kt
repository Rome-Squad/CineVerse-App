package com.giraffe.presentation.home.screen.home

import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType


sealed interface HomeEffect {
    data class NavigateToMovieDetails(val movieId: Int) : HomeEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : HomeEffect
    data class NavigateToShowMore(val sectionType: ShowMoreSectionType) :
        HomeEffect

    data object NavigateToExploreScreen : HomeEffect
    data object NavigateToMatchScreen : HomeEffect
    data class NavigateToFeaturedCollection(val collectionId: Int, val collectionTitle: String) :
        HomeEffect

    data class ShowError(val error: Throwable) : HomeEffect
    object NavigateToYourCollection : HomeEffect
    data class NavigateToCollection(
        val collectionId: Int,
        val collectionName: String
    ) : HomeEffect
}