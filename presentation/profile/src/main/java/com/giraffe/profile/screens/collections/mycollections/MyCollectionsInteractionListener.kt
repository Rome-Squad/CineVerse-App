package com.giraffe.profile.screens.collections.mycollections

import com.giraffe.profile.model.CollectionUi

interface MyCollectionsInteractionListener {

    fun onCollectionClick(collection: CollectionUi)

    //create new collection bottom sheet
    fun onCreateNewCollectionClick()
    fun onConfirmCreateNewCollectionClick()
    fun onDismissCreateNewCollectionBottomSheet()

    fun onStartCollectingClick()

    fun onBackClick()
}