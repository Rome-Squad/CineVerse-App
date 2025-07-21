package com.giraffe.details.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.castDetails.castDetailsRoute
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.reviewScreen.REVIEW_LIST_ARG
import com.giraffe.details.screens.reviewScreen.Review_ROUTE
import com.giraffe.details.screens.reviewScreen.reviewRoute
import com.giraffe.details.screens.seriesdetails.seriesDetailsRoute

@Composable
fun DetailsNavGraph(
    navController: NavHostController,
    startDestinationRoute: String,
    back: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
    ) {
        movieDetailsRoute(
            navController = navController,
            onBackButtonClick = {
                val backed = navController.navigateUp()
                if (!backed) {
                    back()
                }
            },
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
            },
            onBackButtonClick = {
                navController.navigateUp()
            }
        )

        castDetailsRoute(
            navController = navController
        )

        reviewRoute(navController)
    }
}
