package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.details.screens.castCredit.CastCreditScreen
import kotlinx.serialization.Serializable

@Serializable
internal data class CastCreditRoute(val castID: Int, val actorName: String)

internal fun NavController.navigateToCastCredit(castID: Int, actorName: String) {
    navigate(CastCreditRoute(castID, actorName))
}

fun NavGraphBuilder.castCreditRoute(
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable<CastCreditRoute> { backStackEntry ->
        CastCreditScreen(
            onBackClick = onBackClick,
            navigateToSeriesDetails = navigateToSeriesDetails,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }
}