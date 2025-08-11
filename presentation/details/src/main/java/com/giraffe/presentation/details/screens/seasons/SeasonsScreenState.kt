package com.giraffe.presentation.details.screens.seasons

import com.giraffe.presentation.details.model.SeasonUi

data class SeasonsScreenState(
    val seriesId: Int? = null,
    val seasons: List<SeasonUi> = emptyList(),
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false
)
