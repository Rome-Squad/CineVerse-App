package com.giraffe.home.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable


@Serializable
data class SeriesDetailsRoute(val seriesId: Int)

fun NavController.navigateToSeriesDetails(seriesId: Int) {
    navigate(SeriesDetailsRoute(seriesId))
}