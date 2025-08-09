package com.giraffe.presentation.home.screen.home

import androidx.annotation.StringRes


sealed interface HomeEffect {
    data class NavigateToMovieDetails(val movieId: Int) : HomeEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : HomeEffect
    data class NavigateToRecentlyReleasedList(val sectionTitle: String, val sectionType: String) :
        HomeEffect

    data class NavigateToTopRatedList(val sectionTitle: String, val sectionType: String) :
        HomeEffect

    data class NavigateToUpcomingList(val sectionTitle: String, val sectionType: String) :
        HomeEffect

    data class NavigateToRecentlyViewedList(val sectionTitle: String, val sectionType: String) :
        HomeEffect

    data class NavigateToRecommendedList(val sectionTitle: String, val sectionType: String) :
        HomeEffect

    data object NavigateToExploreScreen : HomeEffect
    data object NavigateToMatchScreen : HomeEffect
    data class NavigateToFeaturedCollection(val collectionId: Int, val collectionTitle: String) :
        HomeEffect

    data class ShowError(@param:StringRes val messageRes: Int) : HomeEffect
    object NavigateToYourCollection : HomeEffect
    data class NavigateToCollection(
        val collectionId: Int,
        val collectionName: String
    ) : HomeEffect
}