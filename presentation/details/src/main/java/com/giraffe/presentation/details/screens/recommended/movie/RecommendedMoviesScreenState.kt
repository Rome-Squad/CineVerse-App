package com.giraffe.presentation.details.screens.recommended.movie

import androidx.paging.PagingData
import com.giraffe.media.entity.Genre
import com.giraffe.presentation.details.model.MovieUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class RecommendedMoviesScreenState(
    val movieId: Int? = null,
    val movieTitle: String? = null,
    val movieGenres: List<Genre> = emptyList(),
    val recommendedMoviesFlow: Flow<PagingData<MovieUi>> = flowOf(),
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false
)
