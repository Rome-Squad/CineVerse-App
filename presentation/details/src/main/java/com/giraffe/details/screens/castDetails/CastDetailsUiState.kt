package com.giraffe.details.screens.castDetails

import com.giraffe.designsystem.uimodel.Poster

data class CastDetailsUiState(
    val actorId: Int = 0,
    val actorImageUrl: String = "",
    val actorName: String = "",
    val actorBirth: String = "",
    val actorPlace: String = "",
    val actorYouTubeLink: String = "",
    val actorFacebookLink: String = "",
    val actorInstagramLink: String = "",
    val biographyInfo: String = "",
    val actorGalleryImageUrls: List<String?> = emptyList(),
    val posters: List<Poster> = emptyList(),
    val isLoading: Boolean = true,
)