package com.giraffe.profile.screens.ratings

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.profile.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val getRatedMoviesUseCase: GRMUC,
):
    BaseViewModel<RatingScreenState, RatingEffect>(RatingScreenState()),
    RatingInteractionListener {

    override fun onPosterClick(poster: Poster) {
        if (poster.mediaTypeOfPoster == Poster.Type.MOVIE.value) {
            sendEffect(RatingEffect.NavigateToMovieDetails(poster.id))
        }

        if (poster.mediaTypeOfPoster == Poster.Type.SERIES.value) {
            sendEffect(RatingEffect.NavigateToSeriesDetails(poster.id))
        }

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