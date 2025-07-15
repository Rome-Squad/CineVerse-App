package com.giraffe.details.screens.castDetails

import com.giraffe.details.base.BaseViewModel

class CastDetailsViewModel(

) : BaseViewModel<CastDetailsUiState, CostDetailsEffect>(CastDetailsUiState()),
    CastDetailsInteractionListener {
    override fun onActorYoutubeLinkClicked() {
        TODO("Not yet implemented")
    }

    override fun onActorFacebookLinkClicked() {
        TODO("Not yet implemented")
    }

    override fun onActorInstagramLinkClicked() {
        TODO("Not yet implemented")
    }

    override fun onMovieClicked(movieId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToMoviesListScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToActorGalleryScreen() {
        TODO("Not yet implemented")
    }

}