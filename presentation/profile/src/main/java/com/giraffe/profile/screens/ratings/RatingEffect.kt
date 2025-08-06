package com.giraffe.profile.screens.ratings

import androidx.annotation.StringRes

sealed class RatingEffect {
    data class ShowError(@param:StringRes val message: Int) : RatingEffect()
    data class NavigateToDetails(val id: Int) : RatingEffect()
    object NavigateBack : RatingEffect()


}