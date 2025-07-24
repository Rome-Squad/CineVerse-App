package com.giraffe.details.screens.castDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
internal data class CastDetailsRoute(val id: Int)

internal fun NavController.navigateToCastDetails(id: Int) {
    navigate(CastDetailsRoute(id))
}

internal fun NavGraphBuilder.castDetailsRoute(
    navController: NavController,
    onBackButtonClick: () -> Unit,
    ) {
    composable<CastDetailsRoute> { backStackEntry ->
        val personId = backStackEntry.toRoute<CastDetailsRoute>().id

        CastDetailsScreen(
            navController = navController,
            personId = personId,
            onBackButtonClick = onBackButtonClick
        )
    }
}