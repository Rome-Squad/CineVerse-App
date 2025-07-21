package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.details.DetailsStartDestination
import com.giraffe.details.screens.castDetails.CAST_ROUTE
import com.giraffe.details.screens.moviedetails.screen.MOVIES_ROUTE
import com.giraffe.details.screens.seriesdetails.SERIES_ROUTE

class DetailsApiImp : DetailsApi {
    @Composable
    override fun DetailsContainer(
        startDestination: DetailsStartDestination,
        backPress: () -> Unit
    ) {

        val navController: NavHostController = rememberNavController()

        val startDestinationRoute = when (startDestination) {
            is DetailsStartDestination.Movie -> "${MOVIES_ROUTE}/${startDestination.movieId}"
            is DetailsStartDestination.Series -> "${SERIES_ROUTE}/${startDestination.seriesId}"
            is DetailsStartDestination.Cast -> "${CAST_ROUTE}/${startDestination.castId}"
        }
        DetailsNavGraph(
            navController = navController,
            startDestinationRoute = startDestinationRoute,
            back = backPress
        )
    }
}