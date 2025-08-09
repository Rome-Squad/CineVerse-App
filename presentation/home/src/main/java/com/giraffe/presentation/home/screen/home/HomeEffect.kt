package com.giraffe.presentation.home.screen.home

import androidx.annotation.StringRes
import com.giraffe.presentation.home.navigation.show_more.ShowMoreSectionType


sealed interface HomeEffect {
    data class NavigateToMovieDetails(val movieId: Int) : HomeEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : HomeEffect
    data class NavigateToShowMore(val sectionType: ShowMoreSectionType) :
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