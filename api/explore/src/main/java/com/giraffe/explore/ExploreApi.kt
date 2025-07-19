package com.giraffe.explore


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface ExploreApi {
    @Composable
    fun ExploreContainer(
        modifier: Modifier,
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit
    )
}