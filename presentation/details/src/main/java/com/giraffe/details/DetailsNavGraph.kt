package com.giraffe.details

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.giraffe.details.screens.moviedetails.screen.MOVIES_ROUTE
import com.giraffe.details.screens.moviedetails.screen.movieDetailsRoute
import com.giraffe.details.screens.reviewScreen.REVIEW_LIST_ARG
import com.giraffe.details.screens.reviewScreen.Review_ROUTE
import com.giraffe.details.screens.reviewScreen.reviewRoute

const val MOVIE_ID = 268

@Composable
fun DetailsNavGraph(navController: NavHostController) {
NavHost(navController = navController, startDestination = "$MOVIES_ROUTE/${MOVIE_ID}"){
        movieDetailsRoute(navController, navigateToReviews = {reviews->
            navController.currentBackStackEntry?.savedStateHandle?.set(REVIEW_LIST_ARG, reviews)
            navController.navigate(Review_ROUTE)
        })
        reviewRoute(navController)
    }
}