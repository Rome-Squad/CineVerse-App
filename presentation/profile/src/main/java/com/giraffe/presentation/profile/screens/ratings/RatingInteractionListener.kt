package com.giraffe.presentation.profile.screens.ratings

import com.giraffe.presentation.profile.model.RatedPoster

interface RatingInteractionListener {
    fun onPosterClick(ratedPoster: RatedPoster)
    fun onCloseTipClick()
    fun onBackClick()
    fun onTabSelected(tabIndex: Int)
    fun onDeleteRatedPosterClick(ratedPoster: RatedPoster)
    fun onStartRatingClick()
}