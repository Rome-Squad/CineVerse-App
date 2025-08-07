package com.giraffe.home.screen.show_more.recently_viewed

import androidx.lifecycle.viewModelScope
import com.giraffe.home.screen.show_more.BaseShowMoreViewModel
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyViewedViewModel @Inject constructor(
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
) : BaseShowMoreViewModel() {

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetRecentlyMoviesViewedSuccess,
                onError = ::onFail,
                block = getRecentlyMoviesUseCase::invoke
            ).join()
            safeExecute(
                onSuccess = ::onGetRecentlySeriesViewed,
                onError = ::onFail,
                block = getRecentlySeriesUseCase::invoke
            )
        }
    }

    private suspend fun onGetRecentlyMoviesViewedSuccess(movies: Flow<List<Movie>>) {
        movies.collectLatest { moviesList ->
            updateState {
                it.copy(
                    isLoading = false,
                    errorMessageRes = null,
                    mediaList = (it.mediaList + moviesList.map(Movie::toPosterUi)).distinctBy { poster -> poster.id }
                )
            }
        }
    }

    private suspend fun onGetRecentlySeriesViewed(series: Flow<List<Series>>) {
        series.collectLatest { seriesList ->
            updateState {
                it.copy(
                    isLoading = false,
                    errorMessageRes = null,
                    mediaList = (it.mediaList + seriesList.map { it.toPosterUi() }).distinctBy { poster -> poster.id }
                )
            }
        }
    }
}