package com.giraffe.details.screens.castDetails

import com.giraffe.designsystem.uimodel.Poster

data class CastDetailsUiState(
    val actorImage: String = "",
    val actorName: String = "",
    val actorBirth: String = "",
    val actorPlace: String = "",
    val actorYouTubeLink: String = "",
    val actorFacebookLink: String = "",
    val actorInstagramLink: String = "",
    val biographyInfo: String = "",
    val actorGalleryImages: List<String?> = emptyList(),
    val posters: List<Poster> = emptyList(),
    val isLoading: Boolean = false,
)