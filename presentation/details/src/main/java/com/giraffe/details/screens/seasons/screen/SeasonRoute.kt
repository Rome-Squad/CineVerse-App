package com.giraffe.details.screens.seasons.screen

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
        SeasonsScreen(
            onBackClick = onBackClick,
        )
    }
}