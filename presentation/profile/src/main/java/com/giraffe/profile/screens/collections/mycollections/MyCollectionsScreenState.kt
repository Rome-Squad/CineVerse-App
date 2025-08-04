package com.giraffe.profile.screens.collections.mycollections

import com.giraffe.profile.model.CollectionUi

data class MyCollectionsScreenState(
    val collections: List<CollectionUi> = emptyList(),
    val isLoading: Boolean = true,
    val isCreateNewCollectionBottomSheetVisible: Boolean = false,

    //Create New Collection Bottom Sheet
    val newCollectionName: String = ""
)
