package com.giraffe.home.navigation.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.navbar.BottomNavigationBar
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.home.navigation.main.CollectionRoute
import com.giraffe.home.navigation.main.ExploreRoute
import com.giraffe.home.navigation.main.ExploreTab
import com.giraffe.home.navigation.main.MatchRoute
import com.giraffe.home.navigation.main.MatchTab
import com.giraffe.home.navigation.main.MovieDetailsRoute
import com.giraffe.home.navigation.main.ProfileRoute
import com.giraffe.home.navigation.main.ProfileTab
import com.giraffe.home.navigation.main.SeriesDetailsRoute
import com.giraffe.home.navigation.main.YourCollectionRoute
import com.giraffe.home.navigation.main.navigateToCollection
import com.giraffe.home.navigation.main.navigateToMovieDetails
import com.giraffe.home.navigation.main.navigateToSeriesDetails
import com.giraffe.home.navigation.main.navigateToYourCollection
import com.giraffe.home.screen.home.HomeRoute
import com.giraffe.home.screen.home.HomeTab
import com.giraffe.home.screen.home.homeRoute
import com.giraffe.home.screen.movies_list.moviesListRoute
import com.giraffe.home.screen.movies_list.navigateToCollectionList
import com.giraffe.home.screen.movies_list.navigateToMoviesList
import com.giraffe.match.MatchApi
import com.giraffe.profile.ProfileApi


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
            startDestination = HomeRoute.route,
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