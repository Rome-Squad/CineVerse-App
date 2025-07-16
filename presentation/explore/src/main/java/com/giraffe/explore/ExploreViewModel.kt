package com.giraffe.explore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.explore.util.exceptionHandler
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.screen.SearchScreenEffect
import com.giraffe.media.explore.usecase.ExploreUseCases
import com.giraffe.media.explore.util.retryIO
import com.giraffe.media.movies.usecase.ClearCacheUseCase
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.media.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.media.person.usecase.GetRecentPeopleUseCase
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.media.series.usecase.ClearRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.media.series.usecase.SearchSeriesByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val getMoviesGenres: GetMoviesGenresUseCase,
    private val getSeriesGenres: GetSeriesGenresUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,
    private val getSeriesByGenresUseCase: GetSeriesByGenresUseCase,


    private val exploreUseCases: ExploreUseCases,
    private val clearCache: ClearCacheUseCase,
    private val searchMovieByName: SearchMovieByNameUseCase,
    private val searchSeriesByName: SearchSeriesByNameUseCase,
    private val searchPeopleByName: SearchPeopleByNameUseCase,
    private val clearRecentSeries: ClearRecentSeriesUseCase,
    private val clearRecentPeople: ClearRecentPeopleUseCase,
    private val getRecentPeople: GetRecentPeopleUseCase,
    private val getRecentlyMovies: GetRecentlyMoviesUseCase,
    private val getRecentSeries: GetRecentSeriesUseCase
) : ViewModel(), ExploreInteractionListener {
    private val _state = MutableStateFlow(ExploreScreenState())
    val state: StateFlow<ExploreScreenState> = _state.asStateFlow()
    private val _uiEvent = MutableSharedFlow<SearchScreenEffect>()
    val uiEvent: SharedFlow<SearchScreenEffect> = _uiEvent
    private val exceptionHandler = exceptionHandler(_state)
    private var debounceJob: Job? = null

    init {
        getGenres()
        getRecent()
    }

    private fun getGenres() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val moviesGenres = getMoviesGenres().map { it.toUi() }
            val seriesGenres = getSeriesGenres().map { it.toUi() }
            _state.update {
                it.copy(
                    selectedGenres = moviesGenres,
                    moviesGenres = moviesGenres,
                    seriesGenres = seriesGenres
                )
            }
            getMoviesByGenres()
            getSeriesByGenres()
        }
    }

    override fun getMoviesByGenres(genresIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val movies =
                getMoviesByGenresUseCase(genresIds).map { it.toPoster(_state.value.moviesGenres) }
            _state.update { it.copy(movieResults = movies) }
            if (_state.value.selectedTab == SearchTab.MOVIES) _state.update {
                it.copy(
                    selectedPosters = movies
                )
            }
        }
    }

    override fun getSeriesByGenres(genresIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val series =
                getSeriesByGenresUseCase(genresIds).map { it.toPoster(_state.value.seriesGenres) }
            _state.update { it.copy(seriesResults = series) }
            if (_state.value.selectedTab == SearchTab.SERIES) _state.update {
                it.copy(
                    selectedPosters = series
                )
            }
        }
    }

    override fun onTabSelected(tabIndex: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedTab = SearchTab.entries[tabIndex],
                    selectedGenre = if (SearchTab.entries[tabIndex] == SearchTab.MOVIES)
                        _state.value.selectedMovieGenre
                    else
                        _state.value.selectedSeriesGenre,
                    selectedPosters = if (SearchTab.entries[tabIndex] == SearchTab.MOVIES)
                        _state.value.movieResults
                    else if (SearchTab.entries[tabIndex] == SearchTab.SERIES)
                        _state.value.seriesResults
                    else
                        _state.value.actorResults,
                )
            }
        }
    }

    override fun onGenreSelected(genre: GenreUi) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value.selectedTab == SearchTab.MOVIES) {
                _state.update { it.copy(selectedGenre = genre, selectedMovieGenre = genre) }
                getMoviesByGenres(
                    if (genre.id == -1) emptyList() else listOf(genre.id)
                )
            } else {
                _state.update { it.copy(selectedGenre = genre, selectedSeriesGenre = genre) }
                getSeriesByGenres(
                    if (genre.id == -1) emptyList() else listOf(genre.id)
                )
            }
        }
    }

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

    override fun onSearchModeChanged(isSearchMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
             _state.update { it.copy(searchTabs =if (isSearchMode) SearchTab.entries else SearchTab.entries.take(2)) }
            _state.update { it.copy(isSearchMode = isSearchMode) }
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
    }
}
