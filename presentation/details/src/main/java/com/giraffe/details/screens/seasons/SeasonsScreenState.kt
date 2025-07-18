package com.giraffe.details.screens.seasons

import com.giraffe.details.models.SeasonUi

data class SeasonsScreenState(
    val seasons: List<SeasonUi> = emptyList(),
    val isLoadingSeason: Boolean = true
)
