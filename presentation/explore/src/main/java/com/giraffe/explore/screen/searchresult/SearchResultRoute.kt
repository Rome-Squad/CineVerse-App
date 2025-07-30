package com.giraffe.explore.screen.searchresult

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        SearchResultScreen(
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToCastDetails = navigateToCastDetails,
            onBackClick = onBackClick
        )
    }
}