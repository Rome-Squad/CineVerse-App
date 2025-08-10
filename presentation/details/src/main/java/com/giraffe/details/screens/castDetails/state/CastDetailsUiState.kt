package com.giraffe.details.screens.castDetails.state

import com.giraffe.designsystem.uimodel.Poster

data class CastDetailsUiState(
    val actorId: Int = 0,
    val actorImageUrl: String = "",
    val actorName: String = "",
    val actorBirth: String = "",
    val actorPlace: String = "",
    val biographyInfo: String = "",
    val actorGalleryImageUrls: List<String> = emptyList(),
    val posters: List<Poster> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val socialMediaUiList: List<SocialMediaUi> = emptyList()
)