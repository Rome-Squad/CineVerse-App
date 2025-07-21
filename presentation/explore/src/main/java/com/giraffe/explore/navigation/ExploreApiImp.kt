package com.giraffe.explore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giraffe.explore.ExploreApi

class ExploreApiImp : ExploreApi {
    @Composable
    override fun ExploreContainer(
        modifier: Modifier,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit
    ) {
        val navController: NavHostController = rememberNavController()
        ExploreNavGraph(
            modifier = modifier,
            navController = navController,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToCastDetails = navigateToCastDetails
        )
    }
}