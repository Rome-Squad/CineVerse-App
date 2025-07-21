package com.giraffe.home

import androidx.annotation.StringRes


sealed interface HomeEffect {

    // Navigation to media details
    data class NavigateToMovieDetails(val movieId: Int) : HomeEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : HomeEffect

    // Navigation to collection types
    data class NavigateToYourCollectionDetails(val collectionId: Int) : HomeEffect
    data class NavigateToFeaturedCollectionDetails(val collectionId: Int) : HomeEffect
    object NavigateToAllYourCollections : HomeEffect
    object NavigateToAllFeaturedCollections : HomeEffect

    // Navigation to media lists
    object NavigateToPopularList : HomeEffect
    object NavigateToRecentlyReleasedList : HomeEffect
    object NavigateToTopRatedList : HomeEffect
    object NavigateToUpcomingList : HomeEffect
    object NavigateToRecentlyViewedList : HomeEffect

    object NavigateToRecommendedList : HomeEffect
    object NavigateToExploreMore : HomeEffect

    // Error
    data class ShowError(@StringRes val messageRes: Int) : HomeEffect
}