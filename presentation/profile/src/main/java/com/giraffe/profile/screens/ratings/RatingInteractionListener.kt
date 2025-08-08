package com.giraffe.profile.screens.ratings

import com.giraffe.profile.model.RatedPoster

interface RatingInteractionListener {
    fun onPosterClick(ratedPoster: RatedPoster)
    fun onCloseTipClick()
    fun onBackClick()
    fun onTabSelected(tabIndex: Int)

    fun onDeleteRatedPosterClick(ratedPoster: RatedPoster)
}