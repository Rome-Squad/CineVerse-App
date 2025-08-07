package com.giraffe.home.screen.show_more.recently_released

import androidx.lifecycle.viewModelScope
import com.giraffe.home.screen.show_more.BaseShowMoreViewModel
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyReleasedViewModel @Inject constructor(
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeriesUseCase: GetRecentlyReleasedSeriesUseCase,
) : BaseShowMoreViewModel() {

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetRecentlyReleasedMoviesSuccess,
                onError = ::onFail,
                block = { getRecentlyReleasedMoviesUseCase(page = 1) }
            ).join()
            safeExecute(
                onSuccess = ::onGetRecentlyReleasedSeriesSuccess,
                onError = ::onFail,
                block = { getRecentlyReleasedSeriesUseCase(page = 1, limit = 10) }
            )
        }
    }

    private fun onGetRecentlyReleasedSeriesSuccess(series: List<Series>) {
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = series.map { it.toPosterUi() }
            )
        }
    }

    private fun onGetRecentlyReleasedMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = movies.map { it.toPosterUi() }
            )
        }
    }
}