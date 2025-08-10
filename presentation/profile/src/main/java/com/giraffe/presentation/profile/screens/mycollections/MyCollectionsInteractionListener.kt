package com.giraffe.presentation.profile.screens.mycollections

import com.giraffe.presentation.profile.model.CollectionUi

interface MyCollectionsInteractionListener {
    fun onCollectionClick(collection: CollectionUi)
    fun onNewCollectionNameChange(newCollectionName: String)
    fun onCreateNewCollectionClick()
    fun onConfirmCreateNewCollectionClick()
    fun onDismissBottomSheet()
    fun onStartCollectingClick()
    fun onBackClick()
}