package com.giraffe.details.screens.seriesdetails

interface SeriesDetailsInteractionListener {
    fun onClickGiveStars()
    fun onLoginClick()
    fun onClickAddToCollection()
    fun onClickCreateCollection()
    fun onDismissAddToCollectionBottomSheet()
    fun onDismissGiveStarsBottomSheet()
    fun onDismissLoginBottomSheet()
    fun navigateToCastDetailsScreen(personId: Int)
    fun navigateToSeasonsScreen(seriesId: Int)
    fun navigateToRecommendedSeriesScreen(seriesId: Int, title: String)
    fun navigateToSeriesDetails(seriesId: Int)
    fun navigateToReviews(seriesId: Int)


    fun addRate()
    fun onRateChange(rate: Int)
}