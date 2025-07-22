package com.giraffe.details.screens.castCredit

import com.giraffe.designsystem.uimodel.Poster

data class CastCreditScreenState(
    val posters: List<Poster> = emptyList(),
    val isLoading: Boolean = false
)
