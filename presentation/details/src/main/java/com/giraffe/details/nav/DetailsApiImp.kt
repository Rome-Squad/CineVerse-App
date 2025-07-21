package com.giraffe.details.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.details.DetailsStartDestination

class DetailsApiImp : DetailsApi {
    @Composable
    override fun DetailsContainer(
        modifier: Modifier,
        startDestination: DetailsStartDestination,
    ) {

        val navController: NavHostController = rememberNavController()

        val startDestinationRoute = when (startDestination) {
            is DetailsStartDestination.Movie -> MovieDetailsRoute(startDestination.movieId)
            is DetailsStartDestination.Series -> SeriesDetailsRoute(startDestination.seriesId)
            is DetailsStartDestination.Cast -> CastDetailsRoute(startDestination.castId)
        }
        Log.d("TAG DetailsContainer:", "$startDestinationRoute")
        DetailsNavGraph(
            modifier = modifier,
            navController = navController,
            startDestinationRoute = startDestinationRoute
        )
    }
}