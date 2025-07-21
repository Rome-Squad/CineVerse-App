package com.giraffe.explore


import androidx.compose.runtime.Composable

interface ExploreApi {
    @Composable
    fun ExploreContainer(
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
        navigateToCastDetails: (Int) -> Unit
    )
}