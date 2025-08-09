package com.giraffe.presentation.profile.screens.collections.mycollections

import com.giraffe.presentation.profile.model.CollectionUi

data class MyCollectionsScreenState(
    val collections: List<CollectionUi> = emptyList(),
    val isLoading: Boolean = true,
    val isCreateNewCollectionBottomSheetVisible: Boolean = false,

    val newCollectionName: String = ""
)
