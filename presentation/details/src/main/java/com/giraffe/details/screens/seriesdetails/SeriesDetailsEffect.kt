package com.giraffe.details.screens.seriesdetails



sealed interface SeriesDetailsEffect {
    data class Error(val error: Throwable) : SeriesDetailsEffect
    data class NavigateToCastDetails(val personId: Int) :
        SeriesDetailsEffect

    data class NavigateToSeasons(val seriesId: Int) :
        SeriesDetailsEffect

    data class NavigateToSeriesDetails(val seriesId: Int) :
        SeriesDetailsEffect
}