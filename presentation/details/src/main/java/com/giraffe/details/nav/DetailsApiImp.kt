package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.details.DetailsStartDestination

class DetailsApiImp : DetailsApi {
    @Composable
    override fun DetailsContainer(
        startDestination: DetailsStartDestination,
        backPress: () -> Unit
    ) {

        val navController: NavHostController = rememberNavController()

        val startDestinationRoute = when (startDestination) {
            is DetailsStartDestination.Movie -> MovieDetailsRoute(startDestination.movieId)
            is DetailsStartDestination.Series -> SeriesDetailsRoute(startDestination.seriesId)
            is DetailsStartDestination.Cast -> CastDetailsRoute(startDestination.castId)
        }
        DetailsNavGraph(
            navController = navController,
            startDestinationRoute = startDestinationRoute,
            back = backPress
        )
    }
}