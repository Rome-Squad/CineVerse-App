package com.giraffe.presentation.home.screen.show_more


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.presentation.home.base.BaseViewModel
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.model.PosterUiModel
import com.giraffe.presentation.home.navigation.show_more.ShowMoreRoute

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowMoreViewModel @Inject constructor(
    private val showMoreFactory: ShowMoreFactory,
    stateSavedStateHandle: SavedStateHandle
) : BaseViewModel<ShowMoreState, ShowMoreEffect>(ShowMoreState()),
    ShowMoreInteractionListener {

    private val sectionType = stateSavedStateHandle.toRoute<ShowMoreRoute>().sectionType

    init {
        loadByStrategy()
    }

    private fun loadByStrategy() {
        safeExecute(
            onError = ::onLoadByStrategyFail,
            onSuccess = ::onLoadByStrategySuccess,
        ) {
            showMoreFactory.createStrategy(sectionType).loadData()
        }
    }

    private fun onLoadByStrategySuccess(media: List<PosterUiModel>) {
        updateState {
            it.copy(
                sectionType = sectionType,
                mediaList = media,
                isLoading = false,
                errorMessage = null
            )
        }
    }

    private fun onLoadByStrategyFail(exception: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(ShowMoreEffect.ShowError(exception))
    }


    override fun onViewChanged(isGrid: Boolean) {
        updateState {
            it.copy(isListSelected = !isGrid)
        }
    }

    override fun onMediaClicked(
        mediaId: Int,
        mediaType: MediaType
    ) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(ShowMoreEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(ShowMoreEffect.NavigateToSeriesDetails(mediaId))
        }
    }
}