package com.giraffe.explore.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.designsystem.uimodel.PosterMovie
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.explore.usecase.ExploreUseCases
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.movies.usecase.ClearCacheUseCase
import com.giraffe.movies.usecase.GetMovieGenresUseCase
import com.giraffe.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.person.entity.Person
import com.giraffe.person.usecase.ClearRecentPeopleUseCase
import com.giraffe.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre
import com.giraffe.series.usecase.ClearRecentSeriesUseCase
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
    private val clearCashUseCase: ClearCacheUseCase,
    private val searchMovieByNameUseCase: SearchMovieByNameUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    private val searchSeriesByNameUseCase: SearchSeriesByNameUseCase,
    private val clearRecentSeriesUseCase: ClearRecentSeriesUseCase,
    private val searchPeopleByNameUseCase: SearchPeopleByNameUseCase,
    private val clearRecentPeopleUseCase: ClearRecentPeopleUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private var searchDebounceJob: Job? = null
    private var searchApiJob: Job? = null


    fun onIntents(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OnSearchQueryChange -> handleSearchQueryChange(intent.query)
            is SearchIntent.OnClearSearchQuery -> handleClearSearchQuery()
            is SearchIntent.OnClickItem -> handleOnChooseSuggestion(
                intent.suggestion,
                _state.value.selectedTab
            )

            is SearchIntent.OnClearHistory -> handleOnClearHistory()
            is SearchIntent.OnDeleteItemHistory -> handleOnDeleteItemHistory(intent.item)
            is SearchIntent.OnClearRecentViewed -> handleOnClearRecentViewed()
            is SearchIntent.OnSelectedTabChanged -> _state.update { it.copy(selectedTab = intent.tab) }
            is SearchIntent.onClickToggleView -> handleClickToggleView()
            SearchIntent.OnVoiceSearchClick -> _state.update { it.copy(isVoiceRecording = true) }
        }
    }


    private fun handleOnDeleteItemHistory(item: SearchKeyword) {
        viewModelScope.launch {
            exploreUseCases.deleteSearchKeyword.execute(item)
            exploreUseCases.getSearchKeywords.execute(_state.value.searchQuery)
        }
    }

    private fun handleOnClearHistory() {
        viewModelScope.launch {
            exploreUseCases.clearSearchHistory.execute()
            _state.update { it.copy(resultSearchKeyword = emptyList()) }
        }
    }

    private fun handleClickToggleView() {
        _state.update { currentState ->
            currentState.copy(
                isGridSelected = !currentState.isGridSelected,
            )
        }
    }

    private fun handleOnChooseSuggestion(suggestion: SearchKeywordResults, tab: SearchTab) {
        viewModelScope.launch {
            when (tab) {
                SearchTab.MOVIES -> {
                    val movies: List<Movie> = searchMovieByNameUseCase(suggestion.keyword)
                    val genres: List<MovieGenre> = getMovieGenresUseCase()

                    val posterMovies: List<PosterMovie> = movies.map { movie ->
                        val genreTitles = genres
                            .filter { genre -> movie.genresID.contains(genre.id) }
                            .joinToString(separator = ", ") { it.title }

                        val timeString = movie.duration?.let { "${it} min" }

                        val dateString = movie.releaseYear?.toString()

                        PosterMovie(
                            title = movie.title,
                            imageUri = movie.posterUrl.orEmpty(),
                            rating = movie.rating,
                            genres = if (genreTitles.isNotBlank()) genreTitles else null,
                            time = timeString,
                            date = dateString
                        )
                    }
                    _state.update {
                        it.copy(
                            movieResults = posterMovies,
                            isSearchResultsVisible = true,
                            isSearchSuggestionsVisible = false,
                        )
                    }

                }

                SearchTab.SERIES -> {
                    val seriesList: List<Series> = searchSeriesByNameUseCase(suggestion.keyword)
                    val seriesGenres: List<SeriesGenre> = getSeriesGenresUseCase()

                    val posterSeries: List<PosterMovie> = seriesList.map { s ->
                        val genreTitles = seriesGenres
                            .filter { g -> s.genreIDs.contains(g.id) }
                            .joinToString(", ") { it.name }

                        PosterMovie(
                            title = s.name,
                            imageUri = s.posterUrl,
                            rating = s.rating,
                            genres = genreTitles.ifBlank { null },
                        )
                    }

                    _state.update {
                        it.copy(
                            seriesResults = posterSeries,
                            isSearchResultsVisible = true,
                            isSearchSuggestionsVisible = false
                        )
                    }
                }

                SearchTab.ACTORS -> {
                    val people: List<Person> = searchPeopleByNameUseCase(suggestion.keyword)

                    val posterPeople: List<PosterMovie> = people.map { p ->
                        PosterMovie(
                            title = p.name,
                            imageUri = p.imageUrl.orEmpty(),
                            rating = 0.0f,
                        )
                    }

                    _state.update {
                        it.copy(
                            actorResults = posterPeople,
                            isSearchResultsVisible = true,
                            isSearchSuggestionsVisible = false
                        )
                    }
                }
            }
        }
    }

    private fun handleOnClearRecentViewed() {
        viewModelScope.launch {
            clearRecentSeriesUseCase()
            clearRecentPeopleUseCase()
            clearCashUseCase()
            _state.update {
                it.copy(
                    recentViews = emptyList()
                )
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
                val result = exploreUseCases.getSearchKeywords.execute(query)
                _state.update {
                    it.copy(
                        resultSearchKeyword = result.map { it.toSearchKeywordResults() },
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        } else {
            _state.update {
                it.copy(
                    isSearchHistoryVisible = true,
                    isSearchResultsVisible = false,
                    isSearchSuggestionsVisible = false,
                    movieResults = emptyList(),
                    seriesResults = emptyList(),
                    actorResults = emptyList()
                )
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
                resultSearchKeyword = emptyList(),
                movieResults = emptyList(),
                seriesResults = emptyList(),
                actorResults = emptyList(),
            )
        }
    }
}




