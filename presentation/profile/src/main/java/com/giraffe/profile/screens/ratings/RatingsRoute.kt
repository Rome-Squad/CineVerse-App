package com.giraffe.profile.screens.ratings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal object RatingsRoute

internal fun NavController.navigateToRatings() {
    navigate(RatingsRoute)
}

fun NavGraphBuilder.ratingsRoute(

) {
    composable<RatingsRoute> { backStackEntry ->
        RatingsScreen(
        )
    }
}