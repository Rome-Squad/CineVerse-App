package com.giraffe.home.screen.show_more.recommendations

import androidx.lifecycle.viewModelScope
import com.giraffe.home.screen.show_more.BaseShowMoreViewModel
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesYourVibeViewModel @Inject constructor(
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
) : BaseShowMoreViewModel() {

    override fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetRecommendedMoviesSuccess,
                onError = ::onFail,
                block = {
                    getRecentlyMoviesUseCase().first().firstOrNull()?.id?.let { movieId ->
                        getRecommendedMovieUseCase(movieId = movieId, page = 1)
                    } ?: emptyList()
                }
            ).join()
            safeExecute(
                onSuccess = ::onGetRecommendedSeriesSuccess,
                onError = ::onFail,
                block = {
                    getRecentlySeriesUseCase().first().firstOrNull()?.id?.let { seriesId ->
                        getRecommendedSeriesUseCase(seriesId = seriesId, page = 1)
                    } ?: emptyList()
                }
            )
        }
    }

    private fun onGetRecommendedMoviesSuccess(recommendedMovies: List<Movie>) {
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = (it.mediaList + recommendedMovies.map(Movie::toPosterUi)).distinctBy { poster -> poster.id },
            )
        }
    }

    private fun onGetRecommendedSeriesSuccess(recommendedSeries: List<Series>) {
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = (it.mediaList + recommendedSeries.map(Series::toPosterUi)).distinctBy { poster -> poster.id },
            )
        }
    }
}