package com.giraffe.details.screens.castDetails

sealed interface CastDetailsEffect {
    data class Error(val error: Throwable) : CastDetailsEffect
    data class OpenUrl(val url: String) : CastDetailsEffect
    data class NavigateToGallery(val actorName: String, val imageUrls: List<String?>) :
        CastDetailsEffect

    data class NavigateToCastCredit(val castID: Int, val actorName: String) :
        CastDetailsEffect

    data class NavigateToSeriesDetails(val seriesId: Int) : CastDetailsEffect
    data class NavigateToMovieDetails(val movieId: Int) : CastDetailsEffect
}