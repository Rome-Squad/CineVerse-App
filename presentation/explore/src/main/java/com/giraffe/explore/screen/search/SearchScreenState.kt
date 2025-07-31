package com.giraffe.explore.screen.search

import com.giraffe.designsystem.uimodel.Poster

data class SearchScreenState(
    val query: String = "",
    val errorMessageRes: Int = 0,
    val keywords: List<String> = emptyList(),
    val recentKeywords: List<String> = emptyList(),
    val recentPosters: List<Poster> = emptyList(),
    val isVoiceRecording: Boolean = false,
    val isPermissionGranted: Boolean = false
)