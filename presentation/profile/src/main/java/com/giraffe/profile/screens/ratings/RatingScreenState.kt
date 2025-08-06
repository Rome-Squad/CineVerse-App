package com.giraffe.profile.screens.ratings

import com.giraffe.designsystem.uimodel.Poster

data class RatingScreenState(
    val isLoading: Boolean = false,
    val isTipVisible: Boolean = true,
    val selectedTabIndex: Int = 0,
    val selectedPosters: List<Poster> = emptyList(),
    val moviesPosters: List<Poster> = emptyList(),
    val seriesPosters: List<Poster> = emptyList(),
)