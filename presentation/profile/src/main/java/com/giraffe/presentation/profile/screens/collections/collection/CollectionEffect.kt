package com.giraffe.presentation.profile.screens.collections.collection

import androidx.annotation.StringRes

sealed class CollectionEffect {
    object NavigateBack: CollectionEffect()
    data class NavigateToMovieDetails(val movieId: Int): CollectionEffect()
    data class ShowError(@param:StringRes val message: Int): CollectionEffect()
}