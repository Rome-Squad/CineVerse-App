package com.giraffe.presentation.profile.screens.collections.mycollections

import com.giraffe.presentation.profile.model.CollectionUi

interface MyCollectionsInteractionListener {

    fun onCollectionClick(collection: CollectionUi)


    fun onNewCollectionNameChange(newCollectionName: String)
    fun onCreateNewCollectionClick()
    fun onConfirmCreateNewCollectionClick()
    fun onDismissCreateNewCollectionBottomSheet()


    fun onStartCollectingClick()

    fun onBackClick()
}