package com.giraffe.details.screens.moviedetails

interface MovieDetailsInteractionListener {

    fun onAddToCollectionClick()

    fun onCreateCollectionClick()

    fun onCollectionClick()

    fun onLoginClick()

    fun onPersonClick(personId: Int)

    fun onShowMoreMoviesClick()

    fun onShowMoreReviewsClick()

    fun onMovieClick(movieId: Int)

    fun onGiveStarsClick()

    fun onAddRatingClick()

    fun onDismissAddToCollectionBottomSheet()

    fun onDismissLoginBottomSheet()

    fun onDismissGiveStarsBottomSheet()

    fun navigateToCastDetailsScreen(personId: Int)

}