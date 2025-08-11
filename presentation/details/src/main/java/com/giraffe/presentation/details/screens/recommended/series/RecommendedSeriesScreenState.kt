package com.giraffe.presentation.details.screens.recommended.series

import androidx.paging.PagingData
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.entity.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class RecommendedSeriesScreenState(
    val seriesId: Int? = null,
    val seriesTitle: String? = null,
    val seriesGenres: List<Genre> = emptyList(),
    val recommendedSeriesFlow: Flow<PagingData<Poster>> = flowOf(),
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false
)
