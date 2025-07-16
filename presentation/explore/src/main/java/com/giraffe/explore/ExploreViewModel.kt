package com.giraffe.explore

import android.util.Log
import com.giraffe.explore.util.exceptionHandler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.screen.SearchScreenEffect
import com.giraffe.media.explore.usecase.ExploreUseCases
import com.giraffe.media.explore.util.retryIO
import com.giraffe.media.movies.usecase.ClearCacheUseCase
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.media.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.media.person.usecase.GetRecentPeopleUseCase
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.media.series.usecase.ClearRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
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
    private val exploreUseCases: ExploreUseCases,

    private val getMoviesGenres: GetMoviesGenresUseCase,
    private val getSeriesGenres: GetSeriesGenresUseCase,

    private val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,

    private val getMovieGenres: GetMovieGenresUseCase,


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
            _state.update { it.copy(moviesGenres = moviesGenres, seriesGenres = seriesGenres) }
            getMoviesByGenres()
        }
    }

    private fun getRecent() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val recentSeries = retryIO {
                getRecentSeries().map { it.toPoster(getSeriesGenres()) }
            }
            val recentPeople = retryIO {
                getRecentPeople().map { it.toPoster() }
            }
            val recentMovies = retryIO {
                getRecentlyMovies().map {
                    it.toPoster(_state.value.moviesGenres)
                }
            }
            _state.update {
                it.copy(recentViews = recentMovies + recentSeries + recentPeople)
            }
        }
    }

    private fun loadMoviesResult(keyword: SearchKeyword) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val results = retryIO {
                searchMovieByName(keyword.keyword).map {
                    it.toPoster(_state.value.moviesGenres)
                }
            }
            _state.update {
                it.copy(
                    movieResults = results,
                    searchKeyword = keyword,
                    isSearchResultsVisible = true,
                    isSearchSuggestionsVisible = false,
                    actorResults = emptyList()
                )
            }
        }
    }

    private fun loadSeriesResults(keyword: SearchKeyword) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }


            val results = retryIO {
                searchSeriesByName(keyword.keyword).map {
                    it.toPoster(getSeriesGenres())
                }
            }
            _state.update {
                it.copy(
                    seriesResults = results,
                    searchKeyword = keyword,
                    isSearchResultsVisible = true,
                    isSearchSuggestionsVisible = false,
                    actorResults = emptyList()
                )
            }
        }
    }

    private fun loadPeopleResults(keyword: SearchKeyword) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val results = retryIO {
                searchPeopleByName(keyword.keyword).map { it.toPoster() }
            }
            _state.update {
                it.copy(
                    actorResults = results,
                    searchKeyword = keyword,
                    isSearchResultsVisible = true,
                    isSearchSuggestionsVisible = false,
                    movieResults = emptyList(),
                    seriesResults = emptyList(),
                )
            }
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

    override fun onSuggestionClick(suggestion: SearchKeyword) {
        loadMoviesResult(suggestion)
        loadSeriesResults(suggestion)
        loadPeopleResults(suggestion)
    }

    override fun onTabSelected(tabIndex: Int) {
        viewModelScope.launch {
            _state.update { it.copy(selectedTab = SearchTab.entries[tabIndex]) }
            /*when (tab) {
                SearchTab.MOVIES -> loadMoviesResult(keyword)
                SearchTab.SERIES -> loadSeriesResults(keyword)
                SearchTab.ACTORS -> loadPeopleResults(keyword)
            }*/
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

    override fun onGenreSelected(genre: GenreUi) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(selectedGenre = genre) }
            getMoviesByGenres(
                if (genre.id == -1) emptyList() else listOf(genre.id)
            )
        }
    }

    private fun getMoviesByGenres(genresIds: List<Int> = emptyList()) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val movies =
                getMoviesByGenresUseCase(genresIds).map { it.toPoster(_state.value.moviesGenres) }
            _state.update { it.copy(movieResults = movies) }
        }
    }

    override fun onFocusChanged(isFocused: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSearchFieldFocused = isFocused) }
        }
    }

    private fun refresh() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _state.update { it.copy(isLoading = true) }

            val keyword = _state.value.searchKeyword
            if (keyword != null) {
                loadMoviesResult(keyword)
                loadSeriesResults(keyword)
                loadPeopleResults(keyword)
                _uiEvent.emit(SearchScreenEffect.RefreshCompleted)
            }
            _state.update { it.copy(isLoading = false) }

        }
    }
}
