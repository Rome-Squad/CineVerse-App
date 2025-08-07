package com.giraffe.profile.screens.ratings

import com.giraffe.designsystem.uimodel.Poster

interface RatingInteractionListener {
    fun onPosterClick(poster: Poster)
    fun onCloseTipClick()
    fun onBackClick()
    fun onTabSelected(tabIndex: Int)

    fun onDeleteRatedPosterClick(poster: Poster)
}