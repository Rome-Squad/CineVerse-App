package com.giraffe.presentation.details.screens.castDetails

interface CastDetailsInteractionListener {

    fun onSocialMediaLinkClick(url: String)

    fun onShowMoreGalleryTextClick()

    fun onShowMoreCastCreditsTextClick(castId: Int, actorName: String)

    fun onPosterClick(mediaId: Int, mediaType: String)

    fun onBackClick()

    fun onRetryClick()
}