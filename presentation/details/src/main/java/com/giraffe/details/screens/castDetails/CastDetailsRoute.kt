package com.giraffe.details.screens.castDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.giraffe.details.nav.CastDetailsRoute

fun NavGraphBuilder.castDetailsRoute(
    navController: NavController
) {
    composable<CastDetailsRoute> { backStackEntry ->
        val personId = backStackEntry.toRoute<CastDetailsRoute>().id

        CastDetailsScreen(
            navController = navController,
            personId = personId
        )
    }
}