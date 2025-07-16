package com.giraffe.details.screens.seriesdetails

interface SeriesDetailsInteractionListener {
    fun showMoreSeason()
    fun showMoreCast()
    fun showMoreCrew()
    fun showMoreRecommendedSeries()
    fun onClickGiveStars()
    fun showMoreReviews()
    fun onClickMovie(movieId: Int)
    fun onClickAddToCollection()
    fun onClickCreateCollection()
}