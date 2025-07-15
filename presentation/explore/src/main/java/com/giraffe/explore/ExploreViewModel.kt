package com.giraffe.explore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.explore.screen.SearchScreenEffect
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.usecase.ExploreUseCases
import com.giraffe.media.explore.util.exceptionHandler
import com.giraffe.media.explore.util.retryIO
import com.giraffe.media.movies.usecase.ClearCacheUseCase
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
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
            val genres = seriesGenres().map { it.toUi() }
            _state.update { it.copy(genres = genres) }
        }
    }

    private fun getRecent() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val recentSeries = retryIO {
                getRecentSeriesUseCase().map { it.toPosterMovie(seriesGenres()) }
            }
            val recentPeople = retryIO {
                getRecentPeopleUseCase().map { it.toPoster() }
            }
            val recentMovies = retryIO {
                getRecentlyMoviesUseCase().map {
                    it.toPosterMovie(movieGenres(it.genresID))
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
                searchMovie(keyword.keyword).map {
                    it.toPosterMovie(movieGenres(it.genresID))
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
                searchSeries(keyword.keyword).map {
                    it.toPosterMovie(seriesGenres())
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
                searchPeople(keyword.keyword).map { it.toPoster() }
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
            clearSeries()
            clearPeople()
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
