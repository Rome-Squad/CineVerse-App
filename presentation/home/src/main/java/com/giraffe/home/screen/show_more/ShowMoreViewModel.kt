package com.giraffe.home.screen.show_more

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.screen.home.MediaType
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowMoreViewModel @Inject constructor(
    private val showMoreFactory: ShowMoreFactory,
    private val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,
    stateSavedStateHandle: SavedStateHandle
) : BaseViewModel<MoviesListUiState, ShowMoreEffect>(MoviesListUiState()),
    ShowMoreInteractionListener {

    private val sectionType = stateSavedStateHandle.toRoute<MoviesListRoute>().sectionType
    private val collectionId = stateSavedStateHandle.toRoute<MoviesListRoute>().collectionId

    init {
        if (!sectionType.isPredefinedSection()) {
            loadMoviesByGenres(collectionId)
        } else {
            loadByStrategy()
        }
    }

    private fun loadByStrategy() {
        safeExecute {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            val strategy = showMoreFactory.createStrategy(sectionType)
            val mediaList = strategy.loadData()
            updateState { it.copy(isLoading = false, mediaList = mediaList) }
        }
    }

    private fun loadMoviesByGenres(genreId: Int) {
        safeExecute {
            val movies = getMoviesByGenresUseCase(genreId = genreId, page = 1)
            updateState { it.copy(isLoading = false, mediaList = movies.map { it.toPosterUi() }) }
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