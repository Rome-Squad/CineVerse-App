package com.giraffe.details.screens.castDetails

interface CastDetailsInteractionListener {
    fun navigateToActorMediaLink(url: String)
    fun navigateToActorGalleryScreen()
    fun navigateToCastCreditScreen(castId: Int, actorName: String)
    fun onPosterClick(mediaId: Int, mediaType: String)

}