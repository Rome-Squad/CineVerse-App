package com.giraffe.presentation.details.screens.castCredit

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.user.entity.ContentPreference

data class CastCreditScreenState(
    val moviesPosters: List<Poster> = emptyList(),
    val seriesPosters: List<Poster> = emptyList(),
    val castId: Int? = null,
    val actorName: String = "",
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val isGridSelected: Boolean = true,
    val moviesGenres: List<Genre> = emptyList(),
    val seriesGenres: List<Genre> = emptyList()
)