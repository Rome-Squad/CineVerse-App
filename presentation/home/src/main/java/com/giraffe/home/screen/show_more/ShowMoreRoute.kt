package com.giraffe.home.screen.show_more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListRoute(
    val collectionId: Int,
    val sectionType: ShowMoreSectionType
)


enum class ShowMoreSectionType {
    RECENTLY_RELEASED,
    TOP_RATED_TV_SHOWS,
    UPCOMING_MOVIES,
    RECENTLY_VIEWED,
    MATCHES_YOUR_VIBES;

    fun isPredefinedSection(): Boolean {
        return when (this) {
            RECENTLY_RELEASED,
            TOP_RATED_TV_SHOWS,
            UPCOMING_MOVIES,
            RECENTLY_VIEWED,
            MATCHES_YOUR_VIBES -> true
        }
    }
}

fun NavController.navigateToMoviesList(sectionType: ShowMoreSectionType, collectionId: String) {
    navigate(
        MoviesListRoute(
            sectionType = sectionType,
            collectionId =5
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