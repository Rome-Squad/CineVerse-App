package com.giraffe.presentation.details.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data class SeasonsRoute(val seriesID: Int)


internal fun NavController.navigateToSeasons(seriesId: Int) {
    navigate(SeasonsRoute(seriesId))
}

fun NavGraphBuilder.seasonsRoute(
    onBackClick: () -> Unit,
) {
    composable<SeasonsRoute> { backStackEntry ->
        _root_ide_package_.com.giraffe.presentation.details.screens.seasons.SeasonsScreen(
            onBackClick = onBackClick,
        )
    }
}