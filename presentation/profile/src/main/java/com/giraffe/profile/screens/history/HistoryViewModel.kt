package com.giraffe.profile.screens.history

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.profile.base.BaseViewModel
import com.giraffe.profile.utils.toPosterUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
) :
    BaseViewModel<HistoryUiState, HistoryEffect>(initialState = HistoryUiState()),
    HistoryInteractionListener {

    init {
        getRecentViewedMovies()
        getRecentViewedSeries()
    }

    private fun getRecentViewedMovies() {
        safeCollect(
            onEmitNewValue = ::onGetRecentMoviesSuccess,
            onError = ::onFail
        ) { getRecentlyMoviesUseCase.invoke() }
    }

    private fun getRecentViewedSeries() {
        safeCollect(
            onEmitNewValue = ::onGetRecentSeriesSuccess,
            onError = ::onFail
        ) { getRecentlySeriesUseCase.invoke() }
    }

    private fun onGetRecentMoviesSuccess(moviesList: List<Movie>) {
        updateMediaList(moviesList.map(Movie::toPosterUi))
    }

    private fun onGetRecentSeriesSuccess(seriesList: List<Series>) {
        updateMediaList(seriesList.map(Series::toPosterUi))
    }

    private fun updateMediaList(newMediaList: List<Poster>) {
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = (it.mediaList + newMediaList).distinctBy { poster -> poster.id }
            )
        }
    }

    private fun onFail(error: Throwable) {
        updateState { it.copy(isLoading = false, errorMsgRes = mapErrorToResource(error)) }
    }



    override fun onDeleteClicked(){
        val updatedList = state.value.mediaList.filter { poster ->
            poster.id != state.value.mediaList.firstOrNull()?.id
        }

        updateState {
            it.copy(
                mediaList = updatedList,
                isSwiped = false
            )
        }
    }

    override fun onCloseClicked() {
        updateState { it.copy(isVisible = false) }    }

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(HistoryEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(HistoryEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun navigateToExploreScreen() {
        sendEffect(HistoryEffect.navigateToExploreScreen())
    }

}


