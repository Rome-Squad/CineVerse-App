package com.giraffe.api.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface DetailsApi {
    @Composable
    fun MovieDetailsContainer(movieId: Int, onBackClick: () -> Unit)

    @Composable
    fun SeriesDetailsContainer(seriesId: Int, onBackClick: () -> Unit)

    @Composable
    fun CastDetailsContainer(castId: Int, onBackClick: () -> Unit)
}