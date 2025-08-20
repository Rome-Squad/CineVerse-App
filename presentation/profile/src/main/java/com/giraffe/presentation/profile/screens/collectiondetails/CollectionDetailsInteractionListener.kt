package com.giraffe.presentation.profile.screens.collectiondetails

interface CollectionDetailsInteractionListener {
    fun onBackClick()

    fun onPosterClick(id: Int)

    fun onDeletePosterClick(id: Int)

    fun onCloseTipClick()

    fun onStartCollectingClick()
}