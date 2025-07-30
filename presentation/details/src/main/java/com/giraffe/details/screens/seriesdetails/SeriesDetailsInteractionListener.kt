package com.giraffe.details.screens.seriesdetails

interface SeriesDetailsInteractionListener {
    fun onClickGiveStars()
    fun onClickAddToCollection()
    fun onDismissAddToCollectionBottomSheet()
    fun onDismissGiveStarsBottomSheet()

    fun navigateToCastDetailsScreen(personId: Int)
    fun navigateToSeasonsScreen(seriesId: Int)
    fun navigateToRecommendedSeriesScreen(seriesId: Int, title: String)
    fun navigateToSeriesDetails(seriesId: Int)
    fun navigateToReviews(seriesId: Int)

}