package com.giraffe.presentation.profile.screens.ratings

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.profile.model.RatedPoster

data class RatingScreenState(
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val isTipVisible: Boolean = true,
    val selectedTabIndex: Int = 0,
    val movieGenres: List<Genre> = emptyList(),
    val seriesGenres: List<Genre> = emptyList(),
    val selectedPosters: List<RatedPoster> = emptyList(),
    val moviesPosters: List<RatedPoster> = emptyList(),
    val seriesPosters: List<RatedPoster> = emptyList(),
)