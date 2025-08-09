package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data class CastDetailsRoute(val id: Int)

internal fun NavController.navigateToCastDetails(id: Int) {
    navigate(CastDetailsRoute(id))
}

internal fun NavGraphBuilder.castDetailsRoute(
    navigateToGallery: (String, Int) -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastCredit: (castID: Int, actorName: String) -> Unit,
    onBackButtonClick: () -> Unit,
) {
    composable<CastDetailsRoute> {
        _root_ide_package_.com.giraffe.presentation.details.screens.castDetails.CastDetailsScreen(
            onBackButtonClick = onBackButtonClick,
            navigateToCastCredit = navigateToCastCredit,
            navigateToGallery = navigateToGallery,
            navigateToMovieDetails = navigateToMovieDetails,
            navigateToSeriesDetails = navigateToSeriesDetails,
        )
    }
}