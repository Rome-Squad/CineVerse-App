package com.giraffe.details.screens.seriesdetails

import com.giraffe.details.models.SeasonUi
import com.giraffe.details.models.SeriesUi

data class SeriesDetailsScreenState(
    val seriesDetails: SeriesUi = SeriesUi(),
    val genres: List<String> = emptyList(),
    val seasons: List<SeasonUi> = emptyList(),
    val isLoadingSeries: Boolean = true,
    val isLoadingSeason: Boolean = true,
    val isLoadingGenres: Boolean = true,
)