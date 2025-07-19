package com.giraffe.details.screens.seriesdetails

import androidx.navigation.NavController
import com.giraffe.details.navigation.SeriesDetailsRoute


fun NavController.navigateToSeriesDetails(seriesId: Int) {
    navigate(SeriesDetailsRoute(seriesId = seriesId))
}