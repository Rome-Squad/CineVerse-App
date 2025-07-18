package com.giraffe.details.screens.castDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.details.DetailsApi
import com.giraffe.details.DetailsNavGraph
import com.giraffe.details.MOVIE_ID
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

    override fun navigateToSeriesDetails(seriesId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToPersonDetails(personId: Int) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun detailsContainer(
        modifier: Modifier
    ) {
        val navController: NavHostController = rememberNavController()

        LaunchedEffect(Unit) {
            detailsNavController = navController
        }
        NavHost(
            navController = navController,
            startDestination = "$MOVIES_ROUTE/${MOVIE_ID}",
            modifier = modifier
        ){
            movieDetailsRoute(navController, navigateToReviews = {reviews->
                navController.currentBackStackEntry?.savedStateHandle?.set(REVIEW_LIST_ARG, reviews)
                navController.navigate(Review_ROUTE)
            })
            reviewRoute(navController)
        }
    }

}
