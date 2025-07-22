package com.giraffe.explore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.explore.ExploreApi

class ExploreApiImp : ExploreApi {
    @Composable
    override fun ExploreContainer(
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit
    ) {
        val navController: NavHostController = rememberNavController()
        ExploreNavGraph(
            navController = navController,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToCastDetails = navigateToCastDetails
        )
    }
}