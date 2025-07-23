package com.giraffe.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.giraffe.home.HomeApi

class HomeApiImp : HomeApi {
    @Composable
    override fun HomeContainer(
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
    ) {
        val navController = rememberNavController()
        HomeNavGraph(
            navController = navController,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
        )
    }
}