package com.giraffe.presentation.profile.screens.mycollections

import com.giraffe.presentation.profile.model.CollectionUiModel

interface MyCollectionsInteractionListener {
    fun onCollectionClick(collection: CollectionUiModel)
    fun onNewCollectionNameChange(newCollectionName: String)
    fun onCreateNewCollectionClick()
    fun onConfirmCreateNewCollectionClick()
    fun onDismissCreateNewCollectionBottomSheet()
    fun onStartCollectingClick()
    fun onBackClick()
}