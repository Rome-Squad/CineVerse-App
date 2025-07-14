package com.giraffe.explore.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.usecase.ExploreUseCases
import com.giraffe.movies.usecase.ClearCacheUseCase
import com.giraffe.movies.usecase.GetMovieGenresUseCase
import com.giraffe.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.person.usecase.GetRecentPeopleUseCase
import com.giraffe.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.series.usecase.ClearRecentSeriesUseCase
import com.giraffe.series.usecase.GetRecentSeriesUseCase
import com.giraffe.series.usecase.GetSeriesGenresUseCase
import com.giraffe.series.usecase.SearchSeriesByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val exploreUseCases: ExploreUseCases,
    private val clearCache: ClearCacheUseCase,
    private val searchMovie: SearchMovieByNameUseCase,
    private val movieGenres: GetMovieGenresUseCase,
    private val searchSeries: SearchSeriesByNameUseCase,
    private val seriesGenres: GetSeriesGenresUseCase,
    private val searchPeople: SearchPeopleByNameUseCase,
    private val clearSeries: ClearRecentSeriesUseCase,
    private val clearPeople: ClearRecentPeopleUseCase,
    private val getRecentPeopleUseCase: GetRecentPeopleUseCase,
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecentSeriesUseCase: GetRecentSeriesUseCase
) : ViewModel(), SearchInteractionListener {

    private val _state = MutableStateFlow(SearchScreenState())
    val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SearchUIEvent>()
    val uiEvent: SharedFlow<SearchUIEvent> = _uiEvent


    private var debounceJob: Job? = null

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val recentSeries = getRecentSeriesUseCase().map {
                    it.toPosterMovie(seriesGenres())
                }

                val recentPeople = getRecentPeopleUseCase().map {
                    it.toPoster()
                }

                val recentMovies = getRecentlyMoviesUseCase().map {
                    it.toPosterMovie(movieGenres(it.genresID))
                }

                _state.update {
                    it.copy(recentViews = recentMovies + recentSeries + recentPeople)
                }

            } catch (e: Exception) {
                _uiEvent.emit(SearchUIEvent.ShowError("Failed to load recent views"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }


    private fun loadResults(keyword: SearchKeyword, tab: SearchTab) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val list = when (tab) {
                    SearchTab.MOVIES -> searchMovie(keyword.keyword).map {
                        it.toPosterMovie(movieGenres(it.genresID))
                    }

                    SearchTab.SERIES -> searchSeries(keyword.keyword).map {
                        it.toPosterMovie(seriesGenres())
                    }

                    SearchTab.ACTORS -> searchPeople(keyword.keyword).map {
                        it.toPoster()
                    }
                }

                _state.update {
                    it.copy(
                        mediaResults = list,
                        searchKeyword = keyword,
                        isSearchResultsVisible = true,
                        isSearchSuggestionsVisible = false
                    )
                }
            } catch (e: Exception) {
                _uiEvent.emit(SearchUIEvent.ShowError("Failed to search for ${keyword.keyword}"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun clearQuery() {
        debounceJob?.cancel()
        _state.update {
            it.copy(
                searchQuery = "",
                isSearchHistoryVisible = true,
                isSearchSuggestionsVisible = false,
                isSearchResultsVisible = false,
                mediaResults = emptyList(),
                resultSearchKeyword = emptyList()
            )
        }
    }


    override fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        debounceJob?.cancel()

        if (query.isBlank()) return clearQuery()

        _state.update {
            it.copy(
                isSearchSuggestionsVisible = true,
                isSearchResultsVisible = false,
                isSearchHistoryVisible = false,
                isLoading = true
            )
        }

        debounceJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1500)
            try {
                exploreUseCases.getSearchKeywords(query)
                    .flowOn(Dispatchers.IO)
                    .collect { keys ->
                        withContext(Dispatchers.Main) {
                            _state.update {
                                it.copy(
                                    resultSearchKeyword = keys,
                                    isLoading = false,
                                    errorMessage = null
                                )
                            }
                        }
                    }
            } catch (e: Exception) {
                _uiEvent.emit(SearchUIEvent.ShowError("Failed to load suggestions"))
                _state.update { it.copy(isLoading = false) }
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
                mediaResults = emptyList(),
                resultSearchKeyword = emptyList()
            )
        }
    }

    override fun onDeleteItemFromHistory(item: SearchKeyword) {
        viewModelScope.launch(Dispatchers.IO) {
            exploreUseCases.deleteSearchKeyword(item)
        }
    }

    override fun onClearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            exploreUseCases.clearSearchHistory()
            _state.update { it.copy(resultSearchKeyword = emptyList()) }
        }
    }

    override fun onVoiceSearchClick() {
        _state.update { it.copy(isVoiceRecording = true) }
    }

    override fun onClearRecentViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            clearSeries(); clearPeople(); clearCache(); _state.update { it.copy(recentViews = emptyList()) }
        }
    }

    override fun onSuggestionClick(suggestion: SearchKeyword) {
        loadResults(suggestion, _state.value.selectedTab)
    }

    override fun onTabSelected(tab: SearchTab) {
        _state.value.searchKeyword?.let { loadResults(it, tab) }
        _state.update { it.copy(selectedTab = tab) }
    }

    override fun onToggleViewClick() {
        _state.update { it.copy(isGridSelected = !it.isGridSelected) }
    }

    override fun onPermissionResult(granted: Boolean) {
        _state.update {
            it.copy(isPermissionGranted = granted)
        }
    }

    override fun onVoiceSearchFinished() {
        _state.update { it.copy(isVoiceRecording = false) }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            try {
                val keyword = _state.value.searchKeyword
                if (keyword != null) {
                    loadResults(keyword, _state.value.selectedTab)
                    _uiEvent.emit(SearchUIEvent.RefreshCompleted)
                }
            } catch (e: Exception) {
                _uiEvent.emit(SearchUIEvent.ShowError("Refresh failed"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}

