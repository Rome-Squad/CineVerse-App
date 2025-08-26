package com.giraffe.api.details

import android.content.Context
import androidx.compose.runtime.Stable

@Stable
interface DetailsApi {
    fun launchMovieDetails(context: Context, movieId: Int)

    fun launchSeriesDetails(context: Context, seriesId: Int)

    fun launchCastDetails(context: Context, castId: Int)
}