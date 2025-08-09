package com.giraffe.presentation.details.screens.seriesdetails

interface SeriesDetailsInteractionListener {

    fun onAddRateButtonClick()

    fun onRateChange(rate: Int)

    fun onGiveStarsCardClick()

    fun onDismissAddToCollectionBottomSheet()

    fun onDismissGiveStarsBottomSheet()

    fun onDismissLoginBottomSheet()

    fun onCastCardClick(personId: Int)

    fun onShowMoreSeasonsTextClick(seriesId: Int)

    fun onShowMoreRecommendedSeriesTextClick(seriesId: Int, title: String)

    fun onSeriesPosterClick(seriesId: Int)

    fun onShowMoreReviewsTextClick(seriesId: Int)

    fun onPlayButtonClick(url: String)

    fun onLoginButtonClick()

    fun onBackButtonClick()
}