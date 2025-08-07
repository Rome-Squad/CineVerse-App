package com.giraffe.home.screen.show_more

import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.screen.home.MediaType

abstract class BaseShowMoreViewModel :
    BaseViewModel<ShowMoreUiState, ShowMoreEffect>(ShowMoreUiState()),
    ShowMoreInteractionListener {

    protected abstract fun loadData()

    protected fun onFail(errorMessage: Int) {
        updateState {
            it.copy(isLoading = false, errorMessageRes = errorMessage)
        }
    }

    override fun onViewChanged(isGrid: Boolean) {
        updateState {
            it.copy(isListSelected = !isGrid)
        }
    }

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        val effect = when (mediaType) {
            MediaType.MOVIE -> ShowMoreEffect.NavigateToMovieDetails(mediaId)
            MediaType.SERIES -> ShowMoreEffect.NavigateToSeriesDetails(mediaId)
        }
        sendEffect(effect)
    }
}