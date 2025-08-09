package com.giraffe.presentation.profile.screens.history

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.DeleteMovieUseCase
import com.giraffe.media.movies.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.DeleteSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.utils.toPosterUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getRecentlyMoviesUseCase: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val deleteSeriesUseCase: DeleteSeriesUseCase
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


    override fun onDeleteClicked(id: Int, mediaType: String) {
        safeExecute(
            onError = ::onFail,
            onSuccess = {
                val updatedList = state.value.mediaList.filterNot { it.id == id }

                updateState {
                    it.copy(
                        mediaList = updatedList,
                        swipedPosterId = null,
                        isSwiped = false
                    )
                }
            }
        ) {
            if (mediaType == "movie") {
                deleteMovieUseCase(id)
            } else if (mediaType == "series") {
                deleteSeriesUseCase(id)
            }
        }

    }


    override fun onCloseClicked() {
        updateState { it.copy(isVisible = false) }
    }

    override fun onMediaClicked(mediaId: Int, mediaType: String) {
        when (mediaType) {
            "movie" -> sendEffect(HistoryEffect.NavigateToMovieDetails(mediaId))
            "series" -> sendEffect(HistoryEffect.NavigateToSeriesDetails(mediaId))
        }

        sendEffect(HistoryEffect.NavigateToMovieDetails(mediaId))
        sendEffect(HistoryEffect.NavigateToSeriesDetails(mediaId))


    }

    override fun navigateToExploreScreen() {
        sendEffect(HistoryEffect.navigateToExploreScreen)
    }

    override fun retry() {
        getRecentViewedMovies()
        getRecentViewedSeries()
    }

}


