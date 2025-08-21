package com.giraffe.presentation.details.screens.castCredit

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.details.components.uimodel.Poster

data class CastCreditScreenState(
    val posters: List<Poster> = emptyList(),
    val castId: Int? = null,
    val actorName: String = "",
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val isGridSelected: Boolean = true,
    val allSeriesGenres: List<Genre> = emptyList(),
    val allMovieGenres: List<Genre> = emptyList()
)
