package com.giraffe.presentation.home.navigation.home.routes

import androidx.navigation.NavController
import kotlinx.serialization.Serializable


@Serializable
internal data class SeriesDetailsRoute(val seriesId: Int)

internal fun NavController.navigateToSeriesDetails(seriesId: Int) {
    navigate(SeriesDetailsRoute(seriesId))
}