package com.giraffe.presentation.details.screens.castDetails

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.presentation.details.model.SocialMediaUi

data class CastDetailsUiState(
    val actorId: Int = 0,
    val actorImageUrl: String = "",
    val actorName: String = "",
    val actorBirth: String = "",
    val actorPlace: String = "",
    val biographyInfo: String = "",
    val actorGalleryImageUrls: List<String?> = emptyList(),
    val posters: List<Poster> = emptyList(),
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val socialMediaUiList: List<SocialMediaUi> = emptyList()
)