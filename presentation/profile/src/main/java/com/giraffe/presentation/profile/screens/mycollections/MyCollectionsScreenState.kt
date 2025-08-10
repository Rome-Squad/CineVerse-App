package com.giraffe.presentation.profile.screens.mycollections

import com.giraffe.presentation.profile.model.CollectionUiModel

data class MyCollectionsScreenState(
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val collections: List<CollectionUiModel> = emptyList(),
    val isBottomSheetVisible: Boolean = false,
    val newCollectionName: String = "",
)