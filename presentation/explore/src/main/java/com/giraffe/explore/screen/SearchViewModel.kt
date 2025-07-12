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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private var debounceJob: Job? = null

    init {
        viewModelScope.launch {

            try {
                val recentSeries = getRecentSeriesUseCase().map { series ->
                    series.toPosterMovie(seriesGenres())
                }

                val recentPeople = getRecentPeopleUseCase().map { series ->
                    series.toPosterMovie()
                }

                val recentMovies = getRecentlyMoviesUseCase().map { movies ->
                    movies.toPosterMovie(movieGenres(movies.genresID))
                }
                _state.update {
                    it.copy(
                        recentViews = recentMovies + recentSeries + recentPeople
                    )
                }

            } catch (_: Exception) {

            }
        }
    }

    fun onIntent(intent: SearchIntent) = try {
        when (intent) {
            is SearchIntent.OnSearchQueryChange -> queryChanged(intent.query)
            is SearchIntent.OnClearSearchQuery -> clearQuery()
            is SearchIntent.OnClickItem -> loadResults(intent.suggestion, _state.value.selectedTab)
            is SearchIntent.OnSelectedTabChanged -> selectTab(intent.tab)
            is SearchIntent.OnClearHistory -> clearHistory()
            is SearchIntent.OnDeleteItemHistory -> deleteHistory(intent.item)
            is SearchIntent.OnClearRecentViewed -> clearRecent()
            is SearchIntent.onClickToggleView -> toggleView()
            SearchIntent.OnVoiceSearchClick -> _state.update { it.copy(isVoiceRecording = true) }
        }
    } catch (_: Exception) {
        _state.update { it.copy(errorMessage = "An error occurred") }
    }

    private fun selectTab(tab: SearchTab) {
        _state.value.searchKeyword?.let { loadResults(it, tab) }
        _state.update { it.copy(selectedTab = tab) }
    }

    private fun loadResults(keyword: SearchKeyword, tab: SearchTab) {
        viewModelScope.launch {
            val list = when (tab) {
                SearchTab.MOVIES -> searchMovie(keyword.keyword).map {
                    it.toPosterMovie(
                        movieGenres(
                            it.genresID
                        )
                    )
                }

                SearchTab.SERIES -> searchSeries(keyword.keyword).map {
                    it.toPosterMovie(
                        seriesGenres()
                    )
                }

                SearchTab.ACTORS -> searchPeople(keyword.keyword).map { it.toPosterMovie() }
            }

            _state.update {
                it.copy(
                    mediaResults = list,
                    searchKeyword = keyword,
                    isSearchResultsVisible = true,
                    isSearchSuggestionsVisible = false
                )
            }
        }
    }

    private fun queryChanged(q: String) {
        _state.update { it.copy(searchQuery = q) }
        debounceJob?.cancel()
        if (q.isBlank()) return clearQuery()

        _state.update {
            it.copy(
                isSearchSuggestionsVisible = true,
                isSearchResultsVisible = false,
                isSearchHistoryVisible = false
            )
        }
        debounceJob = viewModelScope.launch {
            delay(1500)
            exploreUseCases.getSearchKeywords(q).collect { keys ->
                _state.update {
                    it.copy(
                        resultSearchKeyword = keys,
                        isLoading = false,
                        errorMessage = null
                    )
                }
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

    private fun clearHistory() = viewModelScope.launch {
        exploreUseCases.clearSearchHistory(); _state.update { it.copy(resultSearchKeyword = emptyList()) }
    }

    private fun deleteHistory(item: SearchKeyword) = viewModelScope.launch {
        exploreUseCases.deleteSearchKeyword(item)
    }

    private fun clearRecent() = viewModelScope.launch {
        clearSeries(); clearPeople(); clearCache(); _state.update { it.copy(recentViews = emptyList()) }
    }

    private fun toggleView() = _state.update { it.copy(isGridSelected = !it.isGridSelected) }
}

