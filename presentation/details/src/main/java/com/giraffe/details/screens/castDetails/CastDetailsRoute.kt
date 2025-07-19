package com.giraffe.details.screens.castDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giraffe.details.models.ReviewUI

const val CAST_ROUTE = "castDetails"
private const val CAST_ID_ARG = "personID"

fun NavController.navigateToCastDetails(castID: Int) {
    navigate("$CAST_ROUTE/$castID")
}

fun NavGraphBuilder.castDetailsRoute(
    navController: NavController
) {
    composable(
        route = "$CAST_ROUTE/{$CAST_ID_ARG}",
        arguments = listOf(
            navArgument(CAST_ID_ARG) {
                type = NavType.IntType
            })) { backStackEntry ->
        val personId = backStackEntry.arguments?.getInt(CAST_ID_ARG) ?: 268
        CastDetailsScreen(
            navController = navController,
            personId = personId
        )
    }
}
