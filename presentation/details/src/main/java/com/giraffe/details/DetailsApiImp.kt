package com.giraffe.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.screens.moviedetails.screen.MOVIES_ROUTE
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.moviedetails.screen.navigateToMovieDetails
import com.giraffe.details.screens.reviewScreen.REVIEW_LIST_ARG
import com.giraffe.details.screens.reviewScreen.Review_ROUTE
import com.giraffe.details.screens.reviewScreen.reviewRoute

class DetailsApiImp : DetailsApi {
    private var detailsNavController by mutableStateOf<NavHostController?>(null)

    override fun navigateToMovieDetails(movieId: Int) {
        detailsNavController?.navigateToMovieDetails(movieId)

    }

    @Composable
    override fun DetailsContainer(
        modifier: Modifier,
        startDestination: DetailsStartDestination
    ) {
        val navController: NavHostController = rememberNavController()

        LaunchedEffect(Unit) {
            detailsNavController = navController
        }

        val startDestinationRoute = when (startDestination) {
            is DetailsStartDestination.Movie -> "${MOVIES_ROUTE}/${startDestination.movieId}"
            is DetailsStartDestination.Series -> "SERIES_ROUTE/${startDestination.seriesId}"
            is DetailsStartDestination.Cast -> "CAST_ROUTE/${startDestination.castId}"
        }

        NavHost(
            navController = navController,
            startDestination = startDestinationRoute,
            modifier = modifier
        ) {
            movieDetailsRoute(navController, navigateToReviews = { reviews ->
                navController.currentBackStackEntry?.savedStateHandle?.set(REVIEW_LIST_ARG, reviews)
                navController.navigate(Review_ROUTE)
            })
            reviewRoute(navController)
        }
    }

    /*
        override fun navigateToSeriesDetails(seriesId: Int) {

        }

        override fun navigateToPersonDetails(personId: Int) {

        }*/
}