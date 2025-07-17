package com.giraffe.details.screens.seriesdetails


sealed interface SeriesDetailsEffect {
    data class Error(val error: Throwable) : SeriesDetailsEffect

}