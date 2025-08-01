package com.giraffe.details.screens.castDetails

interface CastDetailsInteractionListener {
    fun onActorYoutubeLinkClicked()
    fun onActorFacebookLinkClicked()
    fun onActorInstagramLinkClicked()
    fun onActorTwitterLinkClicked()
    fun onActorTikTokLinkClicked()
    fun navigateToActorGalleryScreen()
    fun navigateToCastCreditScreen(castId: Int, actorName: String)
    fun onPosterClick(mediaId: Int, mediaType: String)

}