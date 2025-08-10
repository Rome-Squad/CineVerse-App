package com.giraffe.explore.screen.search

import androidx.lifecycle.viewModelScope
import com.giraffe.explore.base.BaseViewModel
import com.giraffe.explore.util.toPoster
import com.giraffe.media.explore.usecase.AddSearchKeywordUseCase
import com.giraffe.media.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.media.explore.usecase.DeleteKeywordUseCase
import com.giraffe.media.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.ClearMoviesCacheUseCase
import com.giraffe.media.movie.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.mediaMember.usecase.ClearRecentPeopleUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.ClearRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchKeywords: GetSearchKeywordsUseCase,
    private val insertSearchKeyword: AddSearchKeywordUseCase,
    private val deleteKeywordUseCase: DeleteKeywordUseCase,
    private val clearSearchHistory: ClearSearchHistoryUseCase,
    private val getRecentlyViewedMoviesUseCase: GetRecentlyViewedMoviesUseCase,
    private val getRecentSeriesUseCase: GetRecentlyViewedSeriesUseCase,
    private val clearRecentSeriesUseCase: ClearRecentlyViewedSeriesUseCase,
    private val clearMoviesCacheUseCase: ClearMoviesCacheUseCase,
    private val clearRecentlyPeopleUseCase: ClearRecentPeopleUseCase
) : BaseViewModel<SearchScreenState>(SearchScreenState()),
    SearchInteractionListener {
    init {
        onQueryChange()
        getRecentViewed()
    }

    private fun onFail(errorMsgRes: Int, isConnected: Boolean) =
        updateState { it.copy(errorMsgRes = errorMsgRes, isConnected = isConnected) }

    private fun getRecentViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            updateState { it.copy(isConnected = true) }
            safeExecute(
                onSuccess = ::onGetRecentlyMoviesSuccess,
                onError = ::onFail,
                block = getRecentlyViewedMoviesUseCase::invoke
            ).join()
            safeExecute(
                onSuccess = ::onGetRecentlySeriesSuccess,
                onError = ::onFail,
                block = getRecentSeriesUseCase::invoke
            )
        }
    }

    private suspend fun onGetRecentlyMoviesSuccess(moviesFlow: Flow<List<Movie>>) {
        moviesFlow.collectLatest { movies ->
            updateState { it.copy(recentPosters = (it.recentPosters + movies.map(Movie::toPoster)).distinctBy { poster -> poster.id }) }
        }
    }

    private suspend fun onGetRecentlySeriesSuccess(seriesFlow: Flow<List<Series>>) {
        seriesFlow.collectLatest { series ->
            updateState { it.copy(recentPosters = (it.recentPosters + series.map(Series::toPoster)).distinctBy { poster -> poster.id }) }
        }
    }

    private var searchJob: Job? = null
    override fun onQueryChange(query: String) {
        updateState { it.copy(isConnected = true) }
        searchJob?.cancel()
        searchJob = safeExecute {
            updateState { it.copy(query) }
            delay(500)
            getSearchKeywords(query).collectLatest { keywords ->
                val recentKeywords = keywords.filter { keyword -> keyword.isRecent }
                updateState {
                    it.copy(
                        keywords = (keywords - recentKeywords).map { searchKeyword -> searchKeyword.keyword },
                        recentKeywords = recentKeywords.map { searchKeyword -> searchKeyword.keyword }
                    )
                }
            }
        }
    }

    override fun onKeywordClick(keyword: String) {
        safeExecute {
            onQueryChange(keyword)
            insertSearchKeyword(keyword)
        }
    }

    override fun deleteKeyword(keyword: String) {
        safeExecute { deleteKeywordUseCase(keyword) }
    }

    override fun clearAllKeywords() {
        safeExecute { clearSearchHistory() }
    }

    override fun clearAllRecentViewedPosters() {
        safeExecute {
            val job1 = launch { clearRecentSeriesUseCase() }
            val job2 = launch { clearMoviesCacheUseCase.clearRecentlyViewedMovies() }
            val job3 = launch { clearRecentlyPeopleUseCase() }
            joinAll(job1, job2, job3)
            updateState { it.copy(recentPosters = emptyList()) }
        }
    }

    override fun onPostfixIconClick() {
        safeExecute {
            if (state.value.query.isNotBlank()) {
                onQueryChange()
            } else {
                updateState { it.copy(isVoiceRecording = true) }
            }
        }
    }

    override fun onPermissionResult(isGranted: Boolean) {
        safeExecute {
            updateState {
                it.copy(isPermissionGranted = isGranted)
            }
        }
    }

    override fun onVoiceSearchFinished() {
        safeExecute {
            updateState {
                it.copy(isVoiceRecording = false)
            }
        }
    }
}