package com.giraffe.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface DetailsApi {
    @Composable
    fun MovieDetailsContainer(movieId: Int)

    @Composable
    fun SeriesDetailsContainer(seriesId: Int)

    @Composable
    fun CastDetailsContainer(castId: Int)
}