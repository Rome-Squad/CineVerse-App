package com.giraffe.explore.screen.search

import com.giraffe.explore.base.BaseViewModel
import com.giraffe.explore.util.toPoster
import com.giraffe.media.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.media.explore.usecase.DeleteKeywordUseCase
import com.giraffe.media.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.media.explore.usecase.InsertSearchKeywordUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.person.usecase.GetRecentPeopleUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class SearchViewModel(
    private val getSearchKeywords: GetSearchKeywordsUseCase,
    private val insertSearchKeyword: InsertSearchKeywordUseCase,
    private val deleteKeywordUseCase: DeleteKeywordUseCase,
    private val clearSearchHistory: ClearSearchHistoryUseCase,
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecentSeriesUseCase: GetRecentSeriesUseCase,
    private val getRecentPeopleUseCase: GetRecentPeopleUseCase,
) : BaseViewModel<SearchScreenState>(SearchScreenState()),
    SearchInteractionListener {
    init {
        getRecentViewedPoster()
        onQueryChange()
    }

    private fun getRecentViewedPoster() {
        safeExecute {
            val recentMovies = async { getRecentlyMoviesUseCase().map { it.toPoster() } }
            val recentSeries = async { getRecentSeriesUseCase().map { it.toPoster() } }
            val recentPeople = async { getRecentPeopleUseCase().map { it.toPoster() } }
            val recentPosters = recentMovies.await() + recentSeries.await() + recentPeople.await()
            updateState { it.copy(recentPosters = recentPosters) }
        }
    }

    private var searchJob: Job? = null
    override fun onQueryChange(query: String) {
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