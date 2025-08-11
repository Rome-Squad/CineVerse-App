package com.giraffe.presentation.details.screens.gallery

data class GalleryUiState (
    val actorId: Int? = null,
    val actorName: String = "",
    val imageUrls: List<String?> = emptyList(),
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false
)