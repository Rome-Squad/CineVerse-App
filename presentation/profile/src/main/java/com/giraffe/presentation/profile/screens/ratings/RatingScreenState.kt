package com.giraffe.presentation.profile.screens.ratings

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.profile.model.RatedPoster
import com.giraffe.user.entity.ContentPreference

data class RatingScreenState(
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val isTipVisible: Boolean = true,
    val isLoggedIn: Boolean = false,
    val selectedTabIndex: Int = 0,
    val movieGenres: List<Genre> = emptyList(),
    val seriesGenres: List<Genre> = emptyList(),
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
    val selectedPosters: List<RatedPoster> = emptyList(),
    val moviesPosters: List<RatedPoster> = emptyList(),
    val seriesPosters: List<RatedPoster> = emptyList(),
)