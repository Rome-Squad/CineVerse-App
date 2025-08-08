package com.giraffe.home.navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.screen.home.HomeRoute
import com.giraffe.home.screen.home.homeRoute
import com.giraffe.home.screen.movies_list.moviesListRoute
import com.giraffe.home.screen.movies_list.navigateToMoviesList


@Composable
fun HomeNavGraph(
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCollection: (collectionId: Int, collectionName: String) -> Unit,
    navigateToYourCollections: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToMatch: () -> Unit,
    onShowBottomBar: (Boolean) -> Unit,
) {
    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.weight(1f)
        ) {

            homeRoute(
                navigateToMoviesScreen = { sectionType, sectionTitle ->
                    navController.navigateToMoviesList(sectionType, sectionTitle)
                    onShowBottomBar(false)
                },
                navigateToMoviesDetailsScreen = navigateToMovieDetails,
                navigateToSeriesDetailsScreen = navigateToSeriesDetails,
                navigateToCollectionList = navigateToCollection/*{ collectionId, collectionTitle ->
                    navController.navigateToCollectionList(
                        collectionId = collectionId,
                        collectionTitle = collectionTitle
                    )
                }*/,
                navigateToYourCollection = navigateToYourCollections,
                navigateToExploreScreen = navigateToExplore/*{
                    navController.navigate(ExploreRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }*/,
                navigateToMatchScreen = navigateToMatch/*{
                    navController.navigate(MatchRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }*/,
                navigateToCollection = navigateToCollection
            )

            moviesListRoute(
                onBackClick = navController::popBackStack,
                navigateToMoviesDetailsScreen = navigateToMovieDetails,
                navigateToSeriesDetailsScreen = navigateToSeriesDetails
            )

        }

    }

}