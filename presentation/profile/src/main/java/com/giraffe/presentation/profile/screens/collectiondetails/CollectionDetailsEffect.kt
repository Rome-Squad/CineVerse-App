package com.giraffe.presentation.profile.screens.collectiondetails

sealed class CollectionDetailsEffect {
    object NavigateBack : CollectionDetailsEffect()
    data class NavigateToMovieDetails(val movieId: Int) : CollectionDetailsEffect()
    data class ShowError(val error: Throwable) : CollectionDetailsEffect()
}