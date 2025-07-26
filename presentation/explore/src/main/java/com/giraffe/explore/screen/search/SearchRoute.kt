package com.giraffe.explore.screen.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.designsystem.uimodel.Poster
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavController.navigateToSearch() {
    navigate(SearchRoute)
}

fun NavGraphBuilder.searchRoute(
    navigateToSearchResult: (String) -> Unit,
    navigateToMovieDetail: (Int) -> Unit,
    navigateToSeriesDetail: (Int) -> Unit,
    navigateToPersonDetail: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    composable<SearchRoute> {
        SearchScreen(
            navigateToSearchResult = navigateToSearchResult,
            onBackClick = onBackClick,
            onClickPoster = { poster ->
                when (poster.mediaTypeOfPoster) {
                    Poster.Type.MOVIE.value -> navigateToMovieDetail(poster.id)
                    Poster.Type.SERIES.value -> navigateToSeriesDetail(poster.id)
                    Poster.Type.PERSON.value -> navigateToPersonDetail(poster.id)
                }
            }
        )
    }
}