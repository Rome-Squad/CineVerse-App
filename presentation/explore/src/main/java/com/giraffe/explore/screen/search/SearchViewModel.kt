package com.giraffe.explore.screen.search

import com.giraffe.explore.base.BaseViewModel
import com.giraffe.media.explore.usecase.ClearSearchHistoryUseCase
import com.giraffe.media.explore.usecase.DeleteKeywordUseCase
import com.giraffe.media.explore.usecase.GetSearchKeywordsUseCase
import com.giraffe.media.explore.usecase.InsertSearchKeywordUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class SearchViewModel(
    private val getSearchKeywords: GetSearchKeywordsUseCase,
    private val insertSearchKeyword: InsertSearchKeywordUseCase,
    private val deleteKeywordUseCase: DeleteKeywordUseCase,
    private val clearSearchHistory: ClearSearchHistoryUseCase
) : BaseViewModel<SearchScreenState>(SearchScreenState()),
    SearchInteractionListener {
    init {
        onQueryChange()
    }

    private var searchJob: Job? = null
    override fun onQueryChange(query: String) {
        searchJob?.cancel()
        searchJob = safeExecute {
            updateState { it.copy(query) }
            delay(500)
            getSearchKeywords(query).collectLatest { keywords ->
                val recentKeywords = keywords.filter { keyword -> keyword.isFromSearchHistory }
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
            }
        }
    }
}

/*





    private val clearCache: ClearCacheUseCase,
    private val searchMovieByName: SearchMovieByNameUseCase,
    private val searchSeriesByName: SearchSeriesByNameUseCase,
    private val searchPeopleByName: SearchPeopleByNameUseCase,
    private val clearRecentSeries: ClearRecentSeriesUseCase,
    private val clearRecentPeople: ClearRecentPeopleUseCase,
    private val getRecentPeople: GetRecentPeopleUseCase,
    private val getRecentlyMovies: GetRecentlyMoviesUseCase,
    private val getRecentSeries: GetRecentSeriesUseCase



override fun onSuggestionClick(suggestion: SearchKeyword) {
        _state.update { it.copy(isSearchFieldFocused = false, searchTabs = SearchTab.entries) }
        loadMoviesResult(suggestion)
        loadSeriesResults(suggestion)
        loadPeopleResults(suggestion)
    }
    private fun loadMoviesResult(keyword: SearchKeyword) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val moviesPosters = searchMovieByName(keyword.keyword).map {
                it.toPoster(_state.value.moviesGenres)
            }
            _state.update {
                it.copy(
                    movieResults = moviesPosters,
                    selectedPosters = moviesPosters,
                    searchKeyword = keyword,
                )
            }
            if (_state.value.selectedTab == SearchTab.MOVIES) _state.update { it.copy(selectedPosters = moviesPosters) }
        }
    }

    private fun loadSeriesResults(keyword: SearchKeyword) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val seriesPosters = searchSeriesByName(keyword.keyword).map {
                it.toPoster(_state.value.seriesGenres)
            }
            _state.update {
                it.copy(
                    seriesResults = seriesPosters,
                    searchKeyword = keyword,
                )
            }
            if (_state.value.selectedTab == SearchTab.SERIES) _state.update { it.copy(selectedPosters = seriesPosters) }
        }
    }

    private fun loadPeopleResults(keyword: SearchKeyword) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val actorsPosters = searchPeopleByName(keyword.keyword).map { it.toPoster() }
            _state.update {
                it.copy(
                    actorResults = actorsPosters,
                    searchKeyword = keyword,
                )
            }
            if (_state.value.selectedTab == SearchTab.ACTORS) _state.update { it.copy(selectedPosters = actorsPosters) }
        }
    }
    private fun getRecent() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val recentSeries = getRecentSeries().map { it.toPoster(_state.value.seriesGenres) }
            val recentPeople = getRecentPeople().map { it.toPoster() }
            val recentMovies = getRecentlyMovies().map { it.toPoster(_state.value.moviesGenres) }
            _state.update {
                it.copy(recentViews = recentMovies + recentSeries + recentPeople)
            }
        }
    }


    override fun onClearSearchQuery() {
        debounceJob?.cancel()
        _state.update {
            it.copy(
                searchQuery = "",
                isSearchHistoryVisible = true,
                isSearchSuggestionsVisible = false,
                isSearchResultsVisible = false,
                movieResults = emptyList(),
                seriesResults = emptyList(),
                actorResults = emptyList(),
                resultSearchKeyword = emptyList()
            )
        }
    }

    override fun onTextChange(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(searchQuery = text) }
        }
    }

    override fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        debounceJob?.cancel()

        if (query.isBlank()) return onClearSearchQuery()

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            exploreUseCases.getSearchKeywords(query)
                .collectLatest { result ->
                    Log.d("messi", "onSearchQueryChange: ${result.map { it.keyword }}")
                    _state.update { it.copy(resultSearchKeyword = result) }
                }
        }
    }

    override fun onDeleteItemFromHistory(item: SearchKeyword) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            exploreUseCases.deleteSearchKeyword(item)
        }
    }

    override fun onClearHistory() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            exploreUseCases.clearSearchHistory()
            _state.update { it.copy(resultSearchKeyword = emptyList()) }
        }
    }

    override fun onVoiceSearchClick() {
        _state.update { it.copy(isVoiceRecording = true) }
    }

    override fun onClearRecentViewed() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            clearRecentSeries()
            clearRecentPeople()
            clearCache()
            _state.update { it.copy(recentViews = emptyList()) }
        }
    }


    override fun onViewChanged(isGrid: Boolean) {
        _state.update { it.copy(isGridSelected = isGrid) }
    }

    override fun onPermissionResult(granted: Boolean) {
        _state.update { it.copy(isPermissionGranted = granted) }
    }

    override fun onVoiceSearchFinished() {
        _state.update { it.copy(isVoiceRecording = false) }
    }

    override fun onFocusChanged(isFocused: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSearchFieldFocused = isFocused) }
        }
    }*/