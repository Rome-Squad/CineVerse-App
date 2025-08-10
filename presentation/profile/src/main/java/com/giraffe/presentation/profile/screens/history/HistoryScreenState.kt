package com.giraffe.presentation.profile.screens.history

import com.giraffe.designsystem.uimodel.Poster


data class HistoryScreenState(
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val mediaList: List<Poster> = emptyList(),
    val historyListTitle: String = "",
    val isSwiped: Boolean = false,
    val swipedPosterId: Int? = null,
    val isTipVisible: Boolean = true,
)

