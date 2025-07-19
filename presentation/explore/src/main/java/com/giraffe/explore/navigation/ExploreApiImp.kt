package com.giraffe.explore.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.explore.ExploreApi
import com.giraffe.explore.navigation.route.navigateToSearchResult
import com.giraffe.explore.screen.discover.DiscoverScreen
import com.giraffe.explore.screen.search.SearchScreen
import com.giraffe.explore.screen.searchresult.SearchResultScreen
import kotlinx.serialization.Serializable

@Serializable
data object DiscoverRoute

@Serializable
data object SearchRoute

@Serializable
data class SearchResultRoute(val query: String)

class ExploreApiImp : ExploreApi {
    override fun NavGraphBuilder.exploreGraph(
        navController: NavHostController,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
    ) {
        composable<DiscoverRoute> {
            DiscoverScreen(
                navController,
                navigateToMovieDetails = navigateToMovieDetails,
                navigateToSeriesDetails = navigateToSeriesDetails
            )
        }
    }

    override fun NavGraphBuilder.searchGraph(
        navController: NavHostController,
        navigateToDetails: (Int) -> Unit
    ) {
        composable<SearchRoute> {
            SearchScreen(
                navigateToSearchResult = {
                    navController.navigateToSearchResult(it)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }

    override fun NavGraphBuilder.searchResultGraph(
        navController: NavHostController,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit,
    ) {
        composable<SearchResultRoute> {
            val query = it.toRoute<SearchResultRoute>().query
            SearchResultScreen(
                query = query,
                onBackClick = {
                    navController.popBackStack()
                },
                onActorClick = navigateToCastDetails,
                onMovieClick = navigateToMovieDetails,
                onSeriesClick = navigateToSeriesDetails,
            )
        }
    }
}