package com.giraffe.profile.screens.ratings

interface RatingInteractionListener {
    fun navigateToDetails(id: Int)
    fun onCloseTipClick()
    fun onBackClick()
    fun onTabSelected(tabIndex: Int)
}