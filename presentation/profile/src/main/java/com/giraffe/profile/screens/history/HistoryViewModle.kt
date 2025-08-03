package com.giraffe.profile.screens.history

import androidx.lifecycle.viewModelScope
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.profile.R
import com.giraffe.profile.base.BaseViewModel
import com.giraffe.profile.utils.toPosterUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HistoryViewModel @Inject constructor(private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
                                           private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
):
    BaseViewModel<HistoryScreenUiStateUiState, HistoryEffect>(initialState = HistoryScreenUiStateUiState()),HistoryInteractionListener{

    init {
        getRecentViewed()
    }

    private fun getRecentViewed() {
        safeCollect(
            onEmitNewValue = ::onGetRecentMoviesSuccess,
            onError = ::onFail
        ) { getRecentlyMoviesUseCase.invoke() }

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

    private fun updateMediaList(newMediaList: List<PosterUiState>) {
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

    private fun mapErrorToResource(error: Throwable): Int {
        return when (error) {
            is NoInternetException -> com.giraffe.profile.R.string.error_network
            else -> com.giraffe.profile.R.string.error_unknown
        }
    }


    override fun onSwipedToLeft() {
        TODO("Not yet implemented")
    }

    override fun onExitClicked() {
        TODO("Not yet implemented")
    }


}