package com.giraffe.home.screen.show_more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListRoute(
    val collectionId: Int? = null,
    val sectionType: String? = null,
    val sectionTitle: String
)

object MovieSectionType {
    const val RECENTLY_RELEASED = "recently_released"
    const val RECENTLY_VIEWED = "recently_viewed"
    const val MATCHES_YOUR_VIBES = "matches_your_vibes"
}

fun NavController.navigateToMoviesList(sectionType: String, sectionTitle: String) {
    navigate(
        MoviesListRoute(
            sectionType = sectionType,
            sectionTitle = sectionTitle,
        )
    )
}

fun NavController.navigateToCollectionList(collectionId: Int, collectionTitle: String) {
    navigate(
        MoviesListRoute(
            collectionId = collectionId,
            sectionTitle = collectionTitle,
        )
    )
}

fun NavGraphBuilder.moviesListRoute(
    onBackClick: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    composable<MoviesListRoute> {
        MoviesListScreen(
            onBackClick = onBackClick,
            navigateToMoviesDetailsScreen = navigateToMoviesDetailsScreen,
            navigateToSeriesDetailsScreen = navigateToSeriesDetailsScreen,
        )
    }
}