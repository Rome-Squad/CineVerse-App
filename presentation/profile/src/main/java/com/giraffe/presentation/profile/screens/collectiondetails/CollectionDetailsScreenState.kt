package com.giraffe.presentation.profile.screens.collectiondetails

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.profile.model.SwipeablePoster
import com.giraffe.user.entity.ContentPreference

data class CollectionDetailsScreenState(
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val collectionId: Int = 0,
    val collectionName: String = "",
    val collectionMovies: List<SwipeablePoster> = emptyList(),
    val movieGenres: List<Genre> = emptyList(),
    val isDeleteTipVisible: Boolean = true,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,

    )
