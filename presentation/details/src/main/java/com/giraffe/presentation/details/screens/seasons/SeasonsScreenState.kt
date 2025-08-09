package com.giraffe.presentation.details.screens.seasons

import com.giraffe.presentation.details.models.SeasonUi

data class SeasonsScreenState(
    val seasons: List<SeasonUi> = emptyList(),
    val isLoading: Boolean = true
)
