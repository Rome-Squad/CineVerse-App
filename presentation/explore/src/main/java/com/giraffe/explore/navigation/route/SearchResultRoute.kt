package com.giraffe.explore.navigation.route

import androidx.navigation.NavController
import com.giraffe.explore.navigation.SearchResultRoute

//const val SEARCH_RESULT_ROUTE = "SEARCH_RESULT_ROUTE"
//const val QUERY = "QUERY"

fun NavController.navigateToSearchResult(query: String) {
    navigate(SearchResultRoute(query))
}
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