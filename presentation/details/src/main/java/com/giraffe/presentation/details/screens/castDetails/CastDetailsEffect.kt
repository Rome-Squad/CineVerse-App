package com.giraffe.presentation.details.screens.castDetails

sealed interface CastDetailsEffect {

    data class ShowError(val exception: Throwable) : CastDetailsEffect

    data class OpenUrl(val url: String) : CastDetailsEffect

    data class NavigateToGallery(val actorName: String, val personId: Int) : CastDetailsEffect

    data class NavigateToCastCredit(val castID: Int, val actorName: String) : CastDetailsEffect

    data class NavigateToSeriesDetails(val seriesId: Int) : CastDetailsEffect

    data class NavigateToMovieDetails(val movieId: Int) : CastDetailsEffect

    object NavigateBack : CastDetailsEffect
}