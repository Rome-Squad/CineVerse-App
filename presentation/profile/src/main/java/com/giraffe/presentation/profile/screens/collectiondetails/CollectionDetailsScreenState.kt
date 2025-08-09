package com.giraffe.presentation.profile.screens.collectiondetails

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.profile.model.SwipeablePoster

data class CollectionDetailsScreenState(
    val collectionId: Int = 0,
    val collectionName: String = "",
    val collectionMovies: List<SwipeablePoster> = emptyList(),
    val movieGenres: List<Genre> = emptyList(),
    val isLoading: Boolean = true,
    val isDeleteTipVisible: Boolean = true,
    val isNoInternet: Boolean = false
)
