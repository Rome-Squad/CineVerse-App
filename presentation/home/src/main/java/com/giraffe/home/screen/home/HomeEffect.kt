package com.giraffe.home.screen.home

import androidx.annotation.StringRes


sealed interface HomeEffect {

    data class NavigateToMovieDetails(val movieId: Int) : HomeEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : HomeEffect

    data class NavigateToYourCollectionDetails(val collectionId: Int) : HomeEffect
    data class NavigateToFeaturedCollectionDetails(val collectionId: Int) : HomeEffect
    object NavigateToAllYourCollections : HomeEffect

    object NavigateToPopularList : HomeEffect
    object NavigateToRecentlyReleasedList : HomeEffect
    object NavigateToTopRatedList : HomeEffect
    object NavigateToUpcomingList : HomeEffect
    object NavigateToRecentlyViewedList : HomeEffect

    object NavigateToRecommendedList : HomeEffect
    object NavigateToExploreMore : HomeEffect

    data class ShowError(@param:StringRes val messageRes: Int) : HomeEffect
}