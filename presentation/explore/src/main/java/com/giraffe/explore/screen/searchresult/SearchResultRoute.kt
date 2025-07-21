package com.giraffe.explore.screen.searchresult

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultRoute(val query: String)

fun NavController.navigateToSearchResult(query: String) {
    navigate(SearchResultRoute(query))
}

fun NavGraphBuilder.searchResultRoute(
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable<SearchResultRoute> { backStackEntry ->
        val query = backStackEntry.toRoute<SearchResultRoute>().query
        SearchResultScreen(
            query = query,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToCastDetails = navigateToCastDetails,
            onBackClick = onBackClick
        )
    }
}