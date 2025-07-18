package com.giraffe.details

import androidx.compose.runtime.Composable

interface DetailsApi {
    @Composable
    fun GetCastDetailsContainer(personId: Int)

    @Composable
    fun GetSeriesDetailsContainer(seriesId: Int)

    @Composable
    fun GetMovieDetailsContainer(movieId: Int)
}