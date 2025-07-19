package com.giraffe.explore

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController


interface ExploreApi {
    fun NavGraphBuilder.exploreGraph(
        navController: NavHostController,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit
    )

    fun NavGraphBuilder.searchGraph(
        navController: NavHostController,
        navigateToDetails: (Int) -> Unit
    )

    fun NavGraphBuilder.searchResultGraph(
        navController: NavHostController,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit
    )
}