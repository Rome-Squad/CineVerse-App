package com.giraffe.presentation.details.screens.gallery

data class GalleryUiState (
    val actorName: String = "",
    val imageUrls: List<String?> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)