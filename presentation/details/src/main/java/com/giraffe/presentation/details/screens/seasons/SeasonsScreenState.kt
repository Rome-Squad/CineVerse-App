package com.giraffe.presentation.details.screens.seasons

import com.giraffe.presentation.details.model.SeasonUi
import com.giraffe.user.entity.ContentPreference

data class SeasonsScreenState(
    val seriesId: Int? = null,
    val seasons: List<SeasonUi> = emptyList(),
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,

    )
