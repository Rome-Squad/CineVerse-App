package com.giraffe.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.movies.entity.Movie
import com.giraffe.person.entity.Person
import com.giraffe.series.entity.Series
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface SearchMoviesUseCase {
    suspend operator fun invoke(query: String): Result<List<Movie>>
}

interface SearchSeriesUseCase {
    suspend operator fun invoke(query: String): Result<List<Series>>
}

interface SearchActorsUseCase {
    suspend operator fun invoke(query: String): Result<List<Person>>
}

interface GetSearchHistoryUseCase {
    suspend operator fun invoke(): Result<List<String>>
}

interface SaveSearchHistoryUseCase {
    suspend operator fun invoke(query: String): Result<Unit>
}

interface RemoveSearchHistoryItemUseCase {
    suspend operator fun invoke(item: String): Result<Unit>
}

interface ClearAllSearchHistoryUseCase {
    suspend operator fun invoke(): Result<Unit>
}

interface GetSearchSuggestionsUseCase {
    suspend operator fun invoke(query: String): Result<List<String>>
}

interface GetRecentViewsUseCase {
    suspend operator fun invoke(): Result<List<MediaStateUi>>
}

interface ClearRecentViewsUseCase {
    suspend operator fun invoke(): Result<Unit>
}

class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchSeriesUseCase: SearchSeriesUseCase,
    private val searchActorsUseCase: SearchActorsUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase,
    private val removeSearchHistoryItemUseCase: RemoveSearchHistoryItemUseCase,
    private val clearAllSearchHistoryUseCase: ClearAllSearchHistoryUseCase,
    private val getSearchSuggestionsUseCase: GetSearchSuggestionsUseCase,
    private val getRecentViewsUseCase: GetRecentViewsUseCase,
    private val clearRecentViewsUseCase: ClearRecentViewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private var searchDebounceJob: Job? = null
    private var searchApiJob: Job? = null

    init {
        loadInitialData()
    }

    fun onIntents(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OnSearchQueryChange -> handleSearchQueryChange(intent.query)
            is SearchIntent.OnClearSearchQuery -> handleClearSearchQuery()
            is SearchIntent.OnClearItemHistory -> handleClearItemHistory(intent.item)
            is SearchIntent.OnClearHistory -> handleClearAllHistory()
            is SearchIntent.OnClearRecentViewed -> handleRecentViewsClear()
            is SearchIntent.OnVoiceSearchClick -> handleVoiceSearchClick()
            is SearchIntent.OnVoiceSearchResult -> handleVoiceSearchResult(intent.result)
            is SearchIntent.OnChooseSuggestion -> handleChooseSuggestionClick(intent.suggestion)
            is SearchIntent.OnSelectedTabChanged -> handleSelectedTabChanged(intent.tab)
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val historyDeferred = viewModelScope.async { getSearchHistoryUseCase() }
            val recentlyViewedDeferred = viewModelScope.async { getRecentViewsUseCase() }

            val historyResult = historyDeferred.await()
            val recentlyViewedResult = recentlyViewedDeferred.await()

            _state.update { currentState ->
                var newState = currentState.copy(isLoading = false)
                historyResult
                    .onSuccess { history -> newState = newState.copy(searchHistory = history) }
                    .onFailure { error -> newState = newState.copy(errorMessage = error.message) }
                recentlyViewedResult
                    .onSuccess { recent -> newState = newState.copy(recentViews = recent) }
                    .onFailure { error -> newState = newState.copy(errorMessage = error.message) }
                newState.copy(isSearchHistoryVisible = newState.searchQuery.isEmpty())
            }
        }
    }

    private fun handleSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)

        searchDebounceJob?.cancel()

        if (query.isNotBlank()) {
            _state.update {
                it.copy(
                    isSearchHistoryVisible = false,
                    isSearchResultsVisible = false,
                    isSearchSuggestionsVisible = true,
                )
            }
            searchDebounceJob = viewModelScope.launch {
                delay(1500)
                fetchSearchSuggestions(query)
                performSearch(query, _state.value.selectedTab)
                saveSearchHistory(query)
            }
        } else {
            _state.update {
                it.copy(
                    isSearchHistoryVisible = true,
                    isSearchResultsVisible = false,
                    isSearchSuggestionsVisible = false,
                    searchSuggestions = emptyList(),
                    movieResults = emptyList(),
                    seriesResults = emptyList(),
                    actorResults = emptyList()
                )
            }
        }
    }

    private fun fetchSearchSuggestions(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            getSearchSuggestionsUseCase(query)
                .onSuccess { suggestions ->
                    _state.update {
                        it.copy(
                            searchSuggestions = suggestions,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.message,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun handleClearSearchQuery() {
        searchDebounceJob?.cancel()
        searchApiJob?.cancel()

        _state.update {
            it.copy(
                searchQuery = "",
                isSearchHistoryVisible = true,
                isSearchResultsVisible = false,
                isSearchSuggestionsVisible = false,
                searchSuggestions = emptyList(),
                movieResults = emptyList(),
                seriesResults = emptyList(),
                actorResults = emptyList(),
            )
        }
    }

    //using flows to update it
    private fun handleClearItemHistory(item: String) {
        viewModelScope.launch {
            removeSearchHistoryItemUseCase(item)
                .onSuccess {
                    _state.update { currentState ->
                        currentState.copy(
                            searchHistory = currentState.searchHistory.filterNot { it == item }
                        )
                    }
                }
                .onFailure { error ->
                    _state.update { currentState -> currentState.copy(errorMessage = error.message) }
                }
        }
    }

    //using flows to update it
    private fun handleClearAllHistory() {
        viewModelScope.launch {
            clearAllSearchHistoryUseCase()
                .onSuccess {
                    _state.update { currentState -> currentState.copy(searchHistory = emptyList()) }
                }
                .onFailure { error ->
                    _state.update { currentState -> currentState.copy(errorMessage = error.message) }
                }
        }
    }

    private fun handleRecentViewsClear() {
        viewModelScope.launch {
            clearRecentViewsUseCase()
                .onSuccess {
                    _state.update { currentState -> currentState.copy(recentViews = emptyList()) }
                }
                .onFailure { error ->
                    _state.update { currentState -> currentState.copy(errorMessage = error.message) }
                }
        }
    }

    private fun handleVoiceSearchClick() {
        _state.update { it.copy(isVoiceRecording = true) }
    }

    private fun handleVoiceSearchResult(result: String) {
        _state.update { it.copy(isVoiceRecording = false, searchQuery = result) }
        if (result.isNotBlank()) {
            performSearch(result, _state.value.selectedTab)
            saveSearchHistory(result)
        }
    }

    private fun handleChooseSuggestionClick(suggestion: String) {
        _state.update { currentState ->
            currentState.copy(
                searchQuery = suggestion,
                isSearchHistoryVisible = false,
                isSearchSuggestionsVisible = false,
            )
        }
        performSearch(suggestion, _state.value.selectedTab)
        saveSearchHistory(suggestion)
    }

    private fun handleSelectedTabChanged(tab: SearchTab) {
        _state.update { it.copy(selectedTab = tab, isLoading = true) }

        val currentQuery = _state.value.searchQuery
        if (currentQuery.isNotBlank()) {
            performSearch(currentQuery, tab)
        } else {
            _state.update {
                it.copy(
                    isLoading = false,
                    isSearchResultsVisible = false,
                    movieResults = emptyList(),
                    seriesResults = emptyList(),
                    actorResults = emptyList()
                )
            }
        }
    }

    private fun performSearch(query: String, tab: SearchTab) {
        searchApiJob?.cancel()
        searchApiJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (tab) {
                SearchTab.MOVIES -> {
                    searchMoviesUseCase(query)
                        .onSuccess { movies ->
                            _state.update { currentState ->
                                currentState.copy(
                                    movieResults = movies.map { it.toMediaStateUi() },
                                    seriesResults = emptyList(),
                                    actorResults = emptyList(),
                                    isLoading = false,
                                    isSearchResultsVisible = true
                                )
                            }
                        }
                        .onFailure {
                            _state.update { it.copy(isLoading = false) }
                        }
                }

                SearchTab.SERIES -> {
                    searchSeriesUseCase(query)
                        .onSuccess { series ->
                            _state.update { currentState ->
                                currentState.copy(
                                    seriesResults = series.map { it.toMediaStateUi() },
                                    movieResults = emptyList(),
                                    actorResults = emptyList(),
                                    isLoading = false,
                                    isSearchResultsVisible = true
                                )
                            }
                        }
                        .onFailure {
                            _state.update { it.copy(isLoading = false) }
                        }
                }

                SearchTab.ACTORS -> {
                    searchActorsUseCase(query)
                        .onSuccess { actors ->
                            _state.update { currentState ->
                                currentState.copy(
                                    actorResults = actors.map { it.toActorStateUi() },
                                    movieResults = emptyList(),
                                    seriesResults = emptyList(),
                                    isLoading = false,
                                    isSearchResultsVisible = true
                                )
                            }
                        }
                        .onFailure {
                            _state.update { it.copy(isLoading = false) }
                        }
                }
            }
        }
    }

    private fun saveSearchHistory(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank() && _state.value.searchHistory.firstOrNull() != query) {
                saveSearchHistoryUseCase(query)
                    .onFailure { _state.update { currentState -> currentState.copy(errorMessage = it.message) } }
            }
        }
    }
}

/////////
//    override fun onClearSearchQuery() {
//        searchApiJob?.cancel()
//        _state.value = _state.value.copy(
//            searchQuery = "",
//            isSearchHistoryVisible = true,
//            isSearchResultsVisible = false,
//            isSearchSuggestionsVisible = false,
////            searchResults = SearchResult(),
//            errorMessage = null
//        )
//    }
//
//
//    override fun onSelectedTabChanged(tabIndex: Int) {
//        val selectedTab = when (tabIndex) {
//            0 -> SearchTab.MOVIES
//            1 -> SearchTab.SERIES
//            2 -> SearchTab.ACTORS
//            else -> SearchTab.MOVIES
//        }
//
//        val currentState = _state.value
//        _state.value = currentState.copy(selectedTab = selectedTab)
//
//        if (currentState.searchQuery.isNotBlank() && !currentState.searchResults.hasDataForTab(
//                selectedTab
//            )
//        ) {
//            performSearchForTab(currentState.searchQuery, selectedTab)
//        }
//    }
//
//    private fun performSearchForCurrentTab(query: String) {
//        val currentTab = _state.value.selectedTab
//        performSearchForTab(query, currentTab)
//    }
//
//    private fun performSearchForTab(query: String, tab: SearchTab) {
//        searchApiJob?.cancel()
//        searchApiJob = viewModelScope.launch {
//            _state.value = _state.value.copy(
//                isLoading = true,
//                errorMessage = null
//            )
//
//            try {
//                val currentResults = _state.value.searchResults
//                val updatedResults = when (tab) {
//                    SearchTab.MOVIES -> {
//                        val movies = searchMoviesUseCase(query)
//                        currentResults.copy(movies = movies)
//                    }
//
//                    SearchTab.SERIES -> {
//                        val series = searchSeriesUseCase(query)
//                        currentResults.copy(series = series)
//                    }
//
//                    SearchTab.ACTORS -> {
//                        val actors = searchActorsUseCase(query)
//                        currentResults.copy(actors = actors)
//                    }
//                }
//
//                _state.value = _state.value.copy(
//                    searchResults = updatedResults,
//                    isSearchResultsVisible = updatedResults.hasDataForTab(tab),
//                    isLoading = false
//                )
//                ////////////////////
//                if (query.isNotBlank()) {
//                    saveSearchHistoryUseCase(query)
//                }
//
//            } catch (e: Exception) {
//                _state.value = _state.value.copy(
//                    errorMessage = e.message,
//                    isLoading = false
//                )
//            }
//        }
//    }
//
//    private fun loadSearchHistory() {
//        viewModelScope.launch {
//            try {
//                val history = getSearchHistoryUseCase()
//                _state.value = _state.value.copy(searchHistory = history)
//            } catch (e: Exception) {
//                _state.value.errorMessage = e.message
//            }
//        }
//}


