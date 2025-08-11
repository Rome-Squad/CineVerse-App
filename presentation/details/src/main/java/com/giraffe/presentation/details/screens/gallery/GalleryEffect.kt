package com.giraffe.presentation.details.screens.gallery

sealed interface GalleryEffect {
    data class ShowError(val error: Throwable) : GalleryEffect
    object NavigateBack : GalleryEffect
}