package com.giraffe.presentation.details.screens.castCredit

import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.user.entity.ContentPreference

data class CastCreditScreenState(
    val posters: List<Poster> = emptyList(),
    val castId: Int? = null,
    val actorName: String = "",
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val isGridSelected: Boolean = true,
)
