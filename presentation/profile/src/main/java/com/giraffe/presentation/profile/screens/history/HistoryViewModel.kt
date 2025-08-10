package com.giraffe.presentation.profile.screens.history

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.DeleteMovieUseCase
import com.giraffe.media.movies.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.DeleteSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.utils.toPoster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getRecentlyMoviesUseCase: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val deleteSeriesUseCase: DeleteSeriesUseCase
) :
    BaseViewModel<HistoryScreenState, HistoryEffect>(initialState = HistoryScreenState()),
    HistoryInteractionListener {

    init {
        getRecentViewedMovies()
        getRecentViewedSeries()
    }

    private fun getRecentViewedMovies() {
        safeCollect(
            onEmitNewValue = ::onGetRecentMoviesSuccess,
            onError = ::onFailure
        ) { getRecentlyMoviesUseCase.invoke() }
    }

    private fun getRecentViewedSeries() {
        safeCollect(
            onEmitNewValue = ::onGetRecentSeriesSuccess,
            onError = ::onFailure
        ) { getRecentlySeriesUseCase.invoke() }
    }

    private fun onGetRecentMoviesSuccess(moviesList: List<Movie>) {
        updateMediaList(moviesList.map(Movie::toPoster))
    }

    private fun onGetRecentSeriesSuccess(seriesList: List<Series>) {
        updateMediaList(seriesList.map(Series::toPoster))
    }

    private fun updateMediaList(newMediaList: List<Poster>) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                mediaList = (it.mediaList + newMediaList).distinctBy { poster -> poster.id }
            )
        }
    }

    private fun onFailure(error: Throwable) {
        updateState { it.copy(isLoading = false, isNoInternet = error is NoInternetException) }
        sendEffect(HistoryEffect.ShowError(error))
    }

    override fun onDeleteClicked(id: Int, mediaType: String) {
        safeExecute(
            onError = ::onFailure,
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
        updateState { it.copy(isTipVisible = false) }
    }

    override fun onMediaClicked(mediaId: Int, mediaType: String) {
        when (mediaType) {
            "movie" -> sendEffect(HistoryEffect.NavigateToMovieDetails(mediaId))
            "series" -> sendEffect(HistoryEffect.NavigateToSeriesDetails(mediaId))
        }

        sendEffect(HistoryEffect.NavigateToMovieDetails(mediaId))
        sendEffect(HistoryEffect.NavigateToSeriesDetails(mediaId))


    }

    override fun onBackClick() {
        sendEffect(HistoryEffect.NavigateToBack)
    }

    override fun navigateToExploreScreen() {
        sendEffect(HistoryEffect.NavigateToExploreScreen)
    }
}