package com.giraffe.match.screen.match_pager

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal object MatchRoutePager

internal fun NavController.navigateToMatchPager() {
    navigate(MatchRoutePager)
}

fun NavGraphBuilder.matchPagerRoute(
    onBackClick: () -> Unit,
    onFinish: () -> Unit
) {
    composable<MatchRoutePager> {
        MatchPagerScreen(
            onBackClick = onBackClick,
            onFinish = onFinish
        )
    }
}
