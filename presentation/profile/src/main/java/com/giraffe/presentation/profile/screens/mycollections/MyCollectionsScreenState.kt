package com.giraffe.presentation.profile.screens.mycollections

import com.giraffe.presentation.profile.model.CollectionUi

data class MyCollectionsScreenState(
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val collections: List<CollectionUi> = emptyList(),
    val isBottomSheetVisible: Boolean = false,
    val newCollectionName: String = "",
    val isLoggedIn: Boolean = false
)