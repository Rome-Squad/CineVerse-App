package com.giraffe.details.screens.recommended

import com.giraffe.designsystem.uimodel.Poster

data class RecommendedScreenState(
    val recommended: List<Poster> = emptyList(),
    val title: String = "",
    val isLoadingRecommended: Boolean = true
)
