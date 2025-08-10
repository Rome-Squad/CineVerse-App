package com.giraffe.presentation.explore.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.giraffe.presentation.explore.screen.search.SearchScreen
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
            navigateToMovieDetails = navigateToMovieDetail,
            navigateToSeriesDetails = navigateToSeriesDetail,
            navigateToPersonDetails = navigateToPersonDetail,
        )
    }
}