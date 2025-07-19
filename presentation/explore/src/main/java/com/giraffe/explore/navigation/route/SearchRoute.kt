package com.giraffe.explore.navigation.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.explore.screen.search.SearchScreen

const val SEARCH_ROUTE = "SEARCH_ROUTE"


fun NavController.navigateToSearch() {
    navigate(SEARCH_ROUTE)
}

fun NavGraphBuilder.searchRoute(navController: NavController) {
    composable(
        SEARCH_ROUTE,
    ) { backStackEntry ->
        SearchScreen(
            navigateToSearchResult = {
                navController.navigateToSearchResult(it)
            }
        )
    }
}