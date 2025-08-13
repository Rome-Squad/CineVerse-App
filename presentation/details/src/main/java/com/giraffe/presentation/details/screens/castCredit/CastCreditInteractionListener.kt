package com.giraffe.presentation.details.screens.castCredit

interface CastCreditInteractionListener {

    fun onBackClick()

    fun onPosterClick(mediaId: Int, mediaType: String)

    fun changeView(isGrid: Boolean)

    fun onRetryClick()
}