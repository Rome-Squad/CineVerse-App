package com.giraffe.explore.navigation.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giraffe.explore.screen.searchresult.SearchResultScreen

//const val SEARCH_RESULT_ROUTE = "SEARCH_RESULT_ROUTE"
//const val QUERY = "QUERY"

//fun NavController.navigateToSearchResult(query: String) {
//    navigate(SearchResultRoute(query))
//}
//
//fun NavGraphBuilder.searchResultRoute(
//    navController: NavController,
//) {
//    composable(
//        "$SEARCH_RESULT_ROUTE/{${QUERY}}",
//        arguments = listOf(navArgument(QUERY) { type = NavType.StringType })
//    ) { backStackEntry ->
//        val query = backStackEntry.arguments?.getString(QUERY) ?: "no query"
//        SearchResultScreen(
//            query = query,
//            navController = navController,
//        )
//    }
//}

const val SEARCH_RESULT_ROUTE = "SEARCH_RESULT_ROUTE"
const val QUERY = "QUERY"

fun NavController.navigateToSearchResult(query: String) {
    navigate("$SEARCH_RESULT_ROUTE/$query")
}

fun NavGraphBuilder.searchResultRoute(
    navController: NavController,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit
) {
    composable(
        "$SEARCH_RESULT_ROUTE/{${QUERY}}",
        arguments = listOf(navArgument(QUERY) { type = NavType.StringType })
    ) { backStackEntry ->
        val query = backStackEntry.arguments?.getString(QUERY) ?: ""
        SearchResultScreen(
            query = query,
            navController = navController,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToCastDetails = navigateToCastDetails
        )
    }
}