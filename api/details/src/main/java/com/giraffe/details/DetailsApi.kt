package com.giraffe.details

import androidx.compose.runtime.Composable


interface DetailsApi {
    @Composable
    fun GetSeriesDetailsContainer(seriesId: Int)

    @Composable
    fun GetMovieDetailsContainer(movieId: Int)
}