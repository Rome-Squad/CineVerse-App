package com.giraffe.profile.screens.history

import androidx.annotation.StringRes

sealed class HistoryEffect{
    data class ShowError(@StringRes val messageResId: Int) : HistoryEffect()
   object navigateToExploreScreen: HistoryEffect()
    data class navigateToProfileScreen(val id:Int): HistoryEffect()
    data class NavigateToMovieDetails(val movieId: Int) : HistoryEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : HistoryEffect()

}