package com.giraffe.presentation.profile.screens.mycollections

import com.giraffe.presentation.profile.model.CollectionUiModel

data class MyCollectionsScreenState(
    val collections: List<CollectionUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val isBottomSheetVisible: Boolean = false,
    val newCollectionName: String = "",
    val isNoInternet: Boolean = false
)