package com.giraffe.details.screens.castDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val PERSON_DETAILS_ROUTE = "personDetails"
private const val PERSON_ID_ARG = "personID"


fun NavController.navigateToPersonDetails(personID: Int) {
    navigate("$PERSON_DETAILS_ROUTE/$personID")
}

fun NavGraphBuilder.personDetailsRoute(
    navController: NavController,
) {
    composable(
        "$PERSON_DETAILS_ROUTE/{${PERSON_ID_ARG}}", arguments = listOf(
            navArgument(PERSON_ID_ARG) {
                type = NavType.IntType
            })
    ) { backStackEntry ->
        val personId = backStackEntry.arguments?.getInt(PERSON_ID_ARG) ?: 1
        CastDetailsScreen(
            personId = personId,
            navController = navController,
        )
    }
}