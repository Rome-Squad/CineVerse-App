package com.giraffe.profile.screens.collections.collection

interface CollectionInteractionListener {
    fun onBackClick()

    fun onPosterClick(id: Int)
    fun onDeletePosterClick(id: Int)

    fun onCloseTipClick()
}