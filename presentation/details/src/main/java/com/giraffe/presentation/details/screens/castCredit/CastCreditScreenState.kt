package com.giraffe.presentation.details.screens.castCredit

import com.giraffe.designsystem.uimodel.Poster

data class CastCreditScreenState(
    val posters: List<Poster> = emptyList(),
    val actorName: String = "",
    val isLoading: Boolean = false,
    val isGridSelected: Boolean = true,
)
