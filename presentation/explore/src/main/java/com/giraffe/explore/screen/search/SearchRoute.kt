package com.giraffe.explore.screen.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavController.navigateToSearch() {
    navigate(SearchRoute)
}

fun NavGraphBuilder.searchRoute(
    navigateToSearchResult: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable<SearchRoute> { backStackEntry ->
        SearchScreen(
            navigateToSearchResult = navigateToSearchResult,
            onBackClick = onBackClick,
        )
    }
}