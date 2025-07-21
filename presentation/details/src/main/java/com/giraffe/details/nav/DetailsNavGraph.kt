package com.giraffe.details.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    modifier: Modifier,
    navController: NavHostController,
    startDestinationRoute: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestinationRoute,
        modifier = modifier
    ) {
        movieDetailsRoute(
            navController = navController,
            onBackButtonClick = {
                navController.navigateUp()
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
