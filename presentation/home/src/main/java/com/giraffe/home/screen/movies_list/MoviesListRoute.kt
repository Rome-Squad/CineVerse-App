package com.giraffe.home.screen.movies_list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MoviesListRoute

fun NavController.navigateToMoviesList() {
    navigate(MoviesListRoute)
}

fun NavGraphBuilder.moviesListRoute(
    onBackClick: () -> Unit,
) {
    composable<MoviesListRoute> { backStackEntry ->
        MoviesListScreen(
            onBackClick = onBackClick
        )
    }
}