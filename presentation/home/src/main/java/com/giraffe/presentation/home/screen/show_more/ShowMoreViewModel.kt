package com.giraffe.presentation.home.screen.show_more


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.presentation.home.base.BaseViewModel
import com.giraffe.presentation.home.screen.home.MediaType

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
        safeExecute {
            updateState { it.copy(sectionType = sectionType,isLoading = true, errorMessage = null) }
            val mediaList = showMoreFactory.createStrategy(sectionType).loadData()
            updateState { it.copy(isLoading = false, mediaList = mediaList) }
        }
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