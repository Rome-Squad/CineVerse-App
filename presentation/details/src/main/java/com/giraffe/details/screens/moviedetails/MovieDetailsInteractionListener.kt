package com.giraffe.details.screens.moviedetails

interface MovieDetailsInteractionListener {

    fun onShowAddToCollectionBottomSheet()

    fun onCreateCollectionButtonClick()

    fun onCollectionClick(collectionId: Int)

    fun onLoginButtonClick()

    fun onShowMoreReviewsTextClick()

    fun onMoviePosterClick(movieId: Int)

    fun onGiveStarsCardClick()

    fun onAddRateButtonClick()

    fun onDismissAddToCollectionBottomSheet()

    fun onDismissLoginBottomSheet()

    fun onDismissGiveStarsBottomSheet()

    fun onCastCardClick(personId: Int)

    fun onShowMoreRecommendedMoviesTextClick(movieId: Int, title: String)

    fun onShowMoreReviewsTextClick(movieId: Int)

    fun onRateChange(rate: Int)

    fun onBackButtonClick()

    fun onPlayButtonClick(url: String)

    fun onNewCollectionNameChange(name: String)

    fun onConfirmCreateNewCollectionClick()

    fun onCancelCreateNewCollectionClick()
}