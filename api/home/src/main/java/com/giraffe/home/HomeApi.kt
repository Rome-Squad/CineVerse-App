package com.giraffe.home


import androidx.compose.runtime.Composable

interface HomeApi {
    @Composable
    fun HomeContainer(
        navigateToMovieDetails: (Int) -> Unit,
        navigateToSeriesDetails: (Int) -> Unit,
    )
}