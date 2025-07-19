package com.giraffe.explore


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface ExploreApi {

    fun navigateToSearch()

    fun navigateToSearchResult(query: String)

    fun navigateToDiscover()


    @Composable
    fun ExploreContainer(
        modifier: Modifier = Modifier,
        navigateToMovieDetails: (Int) -> Unit = {},
        navigateToSeriesDetails: (Int) -> Unit = {},
        navigateToCastDetails: (Int) -> Unit = {}
    )
}