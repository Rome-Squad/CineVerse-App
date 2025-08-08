package com.giraffe.details.screens.seriesdetails

interface SeriesDetailsInteractionListener {
    fun onClickGiveStars()
    fun onLoginClick()


    fun onAddRateButtonClick()

    fun onRateChange(rate: Int)

    fun onGiveStarsCardClick()

    fun onAddToCollectionButtonClick()

    fun onClickCreateCollection()

    fun onDismissAddToCollectionBottomSheet()

    fun onDismissGiveStarsBottomSheet()

    fun onDismissLoginBottomSheet()

    fun onCastCardClick(personId: Int)

    fun onShowMoreSeasonsTextClick(seriesId: Int)

    fun onShowMoreRecommendedSeriesTextClick(seriesId: Int, title: String)

    fun onSeriesPosterClick(seriesId: Int)

    fun onShowMoreReviewsTextClick(seriesId: Int)

    fun onPlayButtonClick(url: String)


    fun onBackButtonClick()
}