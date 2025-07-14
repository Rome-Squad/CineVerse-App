package com.giraffe.details.screens.castDetails

interface CastDetailsInteractionListener {
    fun onActorYoutubeLinkClicked()
    fun onActorFacebookLinkClicked()
    fun onActorInstagramLinkClicked()
    fun onMovieClicked(movieId: Int)
    fun navigateToMoviesListScreen()
    fun navigateToActorGalleryScreen()
}