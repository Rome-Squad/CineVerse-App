package com.giraffe.explore.navigation.route

import androidx.navigation.NavController
import com.giraffe.explore.navigation.SearchRoute

//const val SEARCH_ROUTE = "SEARCH_ROUTE"


fun NavController.navigateToSearch() {
    navigate(SearchRoute)
}

//fun NavGraphBuilder.searchRoute(navController: NavController) {
//    composable(
//        SEARCH_ROUTE,
//    ) { backStackEntry ->
//        SearchScreen(navController = navController)
//    }
//}