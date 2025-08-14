package com.giraffe.match.screen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MatchRouteStart

fun NavGraphBuilder.matchRouteStart(
    onStartMatchingClick: () -> Unit
) {
    composable<MatchRouteStart> {
        MatchScreen(
            onStartMatchingClick = onStartMatchingClick
        )
    }
}
