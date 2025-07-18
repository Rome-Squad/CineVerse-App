package com.giraffe.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


interface DetailsApi {

    fun navigateToMovieDetails(movieId: Int)

    fun navigateToSeriesDetails(seriesId: Int)

    fun navigateToPersonDetails(personId: Int)

    @Composable
    fun detailsContainer(
        modifier: Modifier = Modifier
    )

}