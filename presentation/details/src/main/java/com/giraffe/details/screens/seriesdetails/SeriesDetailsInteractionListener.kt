package com.giraffe.details.screens.seriesdetails

interface SeriesDetailsInteractionListener {
    fun onClickGiveStars()
    fun onClickAddToCollection()
    fun onDismissAddToCollectionBottomSheet()
    fun onDismissGiveStarsBottomSheet()

    fun navigateToCastDetailsScreen(personId: Int)
    fun navigateToSeasonsScreen(personId: Int)
    fun navigateToRecommendedSeriesScreen(seriesId: Int, title: String)
}