package com.giraffe.presentation.explore.screen.search

import com.giraffe.designsystem.uimodel.Poster
data class SearchScreenState(
    val query: String = "",
    val keywords: List<String> = emptyList(),
    val recentKeywords: List<String> = emptyList(),
    val recentPosters: List<Poster> = emptyList(),
    val isVoiceRecording: Boolean = false,
    val isPermissionGranted: Boolean = false,
    val isNoInternet: Boolean = false,
    val isLoading: Boolean = true
)