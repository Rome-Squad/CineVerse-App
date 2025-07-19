package com.giraffe.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.screens.castDetails.CAST_ROUTE
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.moviedetails.screen.MOVIES_ROUTE
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.reviewScreen.REVIEW_LIST_ARG
import com.giraffe.details.screens.reviewScreen.Review_ROUTE
import com.giraffe.details.screens.reviewScreen.reviewRoute
import com.giraffe.details.screens.seriesdetails.SERIES_ROUTE
import com.giraffe.details.screens.seriesdetails.seriesDetailsRoute

class DetailsApiImp : DetailsApi {
    @Composable
    override fun DetailsContainer(
        modifier: Modifier,
        startDestination: DetailsStartDestination
    ) {

        val navController: NavHostController = rememberNavController()

        val startDestinationRoute = when (startDestination) {
            is DetailsStartDestination.Movie -> "${MOVIES_ROUTE}/${startDestination.movieId}"
            is DetailsStartDestination.Series -> "${SERIES_ROUTE}/${startDestination.seriesId}"
            is DetailsStartDestination.Cast -> "${CAST_ROUTE}/${startDestination.castId}"
        }

        println("TAG DetailsContainer: $startDestinationRoute")

        NavHost(
            navController = navController,
            startDestination = startDestinationRoute,
            modifier = modifier
        ) {
            movieDetailsRoute(
                navController = navController,
                navigateToReviews = { reviews ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        REVIEW_LIST_ARG,
                        reviews
                    )
                    navController.navigate(Review_ROUTE)
                }
            )

            seriesDetailsRoute(
                navController = navController,
                navigateToReviews = { reviews ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        REVIEW_LIST_ARG,
                        reviews
                    )
                    navController.navigate(Review_ROUTE)
                }
            )

            castDetailsRoute(
                navController = navController
            )

            reviewRoute(navController)
        }
    }
}