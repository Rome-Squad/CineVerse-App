package com.giraffe.presentation.explore.screen.search

import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.mediaMember.usecase.ClearRecentMediaMembersUseCase
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.ClearMoviesCacheUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.search.usecase.AddSearchKeywordUseCase
import com.giraffe.media.search.usecase.ClearSearchHistoryUseCase
import com.giraffe.media.search.usecase.DeleteSearchKeywordUseCase
import com.giraffe.media.search.usecase.GetSearchKeywordsUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.ClearRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.presentation.explore.base.BaseViewModel
import com.giraffe.presentation.explore.components.uimodel.Poster
import com.giraffe.presentation.explore.screen.search.SearchEffect.NavigateToMovieDetail
import com.giraffe.presentation.explore.util.toPoster
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchKeywords: GetSearchKeywordsUseCase,
    private val insertSearchKeyword: AddSearchKeywordUseCase,
    private val deleteKeywordUseCase: DeleteSearchKeywordUseCase,
    private val clearSearchHistory: ClearSearchHistoryUseCase,
    private val clearRecentlyViewedSeriesUseCase: ClearRecentlyViewedSeriesUseCase,
    private val observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
    private val getRecentSeriesUseCase: GetRecentlyViewedSeriesUseCase,
    private val clearMoviesCacheUseCase: ClearMoviesCacheUseCase,
    private val clearRecentlyPeopleUseCase: ClearRecentMediaMembersUseCase
) : BaseViewModel<SearchScreenState, SearchEffect>(SearchScreenState()),
    SearchInteractionListener {

    init {
        onQueryChange()
        getRecentViewed()
    }

    private fun getRecentViewed() {
        updateState { it.copy(isNoInternet = false, isLoading = true) }

        safeCollect(
            onError = ::onError,
            onEmitNewValue = ::onGetRecentlyMoviesSuccess,
            block = observeRecentlyViewedMoviesUseCase::invoke
        )
        safeCollect(
            onEmitNewValue = ::onGetRecentlySeriesSuccess,
            onError = ::onError,
            block = getRecentSeriesUseCase::invoke
        )
    }

    private fun onGetRecentlyMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                recentPosters = (it.recentPosters + movies.map(Movie::toPoster)).distinctBy { poster -> poster.id },
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun onGetRecentlySeriesSuccess(series: List<Series>) {
        updateState {
            it.copy(
                recentPosters = (it.recentPosters + series.map(Series::toPoster)).distinctBy { poster -> poster.id },
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private var searchJob: Job? = null
    override fun onQueryChange(query: String) {
        updateState { it.copy(isNoInternet = false, isLoading = true) }
        searchJob?.cancel()
        searchJob = safeExecute {
            updateState { it.copy(query) }
            delay(500)
            getSearchKeywords(query).collectLatest { keywords ->
                val recentKeywords = keywords.filter { keyword -> keyword.isFromHistory }
                updateState {
                    it.copy(
                        keywords = (keywords - recentKeywords).map { searchKeyword -> searchKeyword.keyword },
                        recentKeywords = (recentKeywords.map { searchKeyword -> searchKeyword.keyword }),
                        isLoading = false,
                        isNoInternet = false
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
            val job1 = launch { clearRecentlyViewedSeriesUseCase() }
            val job2 = launch { clearMoviesCacheUseCase.clearRecentlyViewed() }
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

    override fun onBackClick() {
        sendEffect(SearchEffect.NavigateBack)
    }

    override fun onClickPoster(poster: Poster) {
        when (poster.mediaTypeOfPoster) {
            Poster.Type.MOVIE.value -> sendEffect(NavigateToMovieDetail(poster.id))
            Poster.Type.SERIES.value -> sendEffect(SearchEffect.NavigateToSeriesDetail(poster.id))
            Poster.Type.PERSON.value -> sendEffect(SearchEffect.NavigateToPersonDetails(poster.id))
        }
    }

    override fun onSearchClick(result: String) {
        if (result.isNotEmpty())
            sendEffect(SearchEffect.NavigateToSearchResult(result))
    }

    override fun onRetryClick() {
        onQueryChange()
        getRecentViewed()
    }

    private fun onError(error: Throwable) {
        updateState {
            it.copy(
                isNoInternet = error is NoInternetException,
                isLoading = false
            )
        }
        sendEffect(SearchEffect.ShowError(error))
    }
}