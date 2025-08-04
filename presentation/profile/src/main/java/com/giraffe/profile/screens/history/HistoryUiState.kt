package com.giraffe.profile.screens.history

import com.giraffe.designsystem.uimodel.Poster


data class HistoryUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mediaList: List<Poster> = emptyList(),
    val historyListTitle: String = "",
    val isSwiped: Boolean = false,
    val errorMsgRes:Int? = null,
    val isVisible: Boolean = false
)

