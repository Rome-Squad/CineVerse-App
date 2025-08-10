package com.giraffe.presentation.profile.screens.collections.collection

import com.giraffe.media.entity.Genre
import com.giraffe.presentation.profile.model.SwipeablePoster

data class CollectionScreenState(
    val collectionId: Int = 0,
    val collectionName: String = "",
    val collectionMovies: List<SwipeablePoster> = emptyList(),
    val movieGenres: List<Genre> = emptyList(),

    val isLoading: Boolean = true,

    val isDeleteTipVisible: Boolean = true,
)
