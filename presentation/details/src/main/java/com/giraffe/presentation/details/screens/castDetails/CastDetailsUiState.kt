package com.giraffe.presentation.details.screens.castDetails

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.model.SocialMediaUi

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
    val isNoInternet: Boolean = false,
    val socialMediaUiList: List<SocialMediaUi> = emptyList(),

    val animationProgress: Animatable<Float, AnimationVector1D> = Animatable(0f)
)