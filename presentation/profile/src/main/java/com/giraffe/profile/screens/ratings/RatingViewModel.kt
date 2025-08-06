package com.giraffe.profile.screens.ratings

import com.giraffe.profile.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RatingViewModel : BaseViewModel<RatingScreenState, RatingEffect>(RatingScreenState()),
    RatingInteractionListener {
    override fun navigateToDetails(id: Int) {
        sendEffect(RatingEffect.NavigateToDetails(id))
    }

    override fun onCloseTipClick() {
        updateState { it.copy(isTipVisible = false) }
    }

    override fun onBackClick() {
        sendEffect(RatingEffect.NavigateBack)
    }

    override fun onTabSelected(tabIndex: Int) {
        updateState {
            it.copy(
                selectedTabIndex = tabIndex,
                selectedPosters = if (tabIndex == 0) it.moviesPosters else it.seriesPosters
            )
        }
    }
}