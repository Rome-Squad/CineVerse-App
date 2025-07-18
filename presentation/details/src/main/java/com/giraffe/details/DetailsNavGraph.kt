package com.giraffe.details

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.moviedetails.screen.MOVIES_ROUTE
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.reviewScreen.REVIEW_LIST_ARG
import com.giraffe.details.screens.reviewScreen.Review_ROUTE
import com.giraffe.details.screens.reviewScreen.reviewRoute
import com.giraffe.details.screens.seriesdetails.SERIES_ROUTE
import com.giraffe.details.screens.seriesdetails.seriesDetailsRoute

const val MOVIE_ID = 268
const val SERIES_ID = 2288

@Composable
fun DetailsNavGraph(navController: NavHostController) {
NavHost(navController = navController, startDestination = "$SERIES_ROUTE/${SERIES_ID}") {
        movieDetailsRoute(navController, navigateToReviews = { reviews ->
            navController.currentBackStackEntry?.savedStateHandle?.set(REVIEW_LIST_ARG, reviews)
            navController.navigate(Review_ROUTE)
        })
        reviewRoute(navController)
        seriesDetailsRoute(navController) { reviews ->
            navController.currentBackStackEntry?.savedStateHandle?.set(REVIEW_LIST_ARG, reviews)
            navController.navigate(Review_ROUTE)
        }
    }
}