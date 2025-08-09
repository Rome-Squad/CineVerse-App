package com.giraffe.presentation.home.screen.show_more

import androidx.annotation.StringRes

sealed class ShowMoreEffect {
    data class NavigateToMovieDetails(val movieId: Int) : ShowMoreEffect()
    data class NavigateToSeriesDetails(val seriesId: Int) : ShowMoreEffect()
    data class ShowError(@StringRes val messageResId: Int) : ShowMoreEffect()
}