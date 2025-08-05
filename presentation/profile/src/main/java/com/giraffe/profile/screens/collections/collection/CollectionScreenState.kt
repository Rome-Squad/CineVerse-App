package com.giraffe.profile.screens.collections.collection

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.profile.model.SwipeablePoster

data class CollectionScreenState(
    val collectionId: Int = 0,
    val collectionName: String = "",
    val collectionMovies: List<SwipeablePoster> = emptyList(),

    val isLoading: Boolean = true,

    val isDeleteTipVisible: Boolean = true,
)
