package com.giraffe.presentation.profile.screens.history

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.profile.uimodel.Poster
import com.giraffe.user.entity.ContentPreference


data class HistoryScreenState(
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val mediaList: List<Poster> = emptyList(),
    val moviesGenres: List<Genre> = emptyList(),
    val seriesGenres: List<Genre> = emptyList(),
    val historyListTitle: String = "",
    val isSwiped: Boolean = false,
    val swipedPosterId: Int? = null,
    val isTipVisible: Boolean = true,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
)

