package com.giraffe.explore.screen.searchresult

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.giraffe.explore.base.BaseViewModel
import com.giraffe.explore.screen.discover.SearchTab
import com.giraffe.explore.util.toPoster
import com.giraffe.explore.util.toUi
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.media.series.usecase.SearchSeriesByNameUseCase

class SearchResultViewModel(
    private val query: String,
    private val searchMovieByName: SearchMovieByNameUseCase,
    private val searchSeriesByName: SearchSeriesByNameUseCase,
    private val searchPeopleByName: SearchPeopleByNameUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
) : BaseViewModel<SearchResultScreenState>(SearchResultScreenState(query = query)),
    SearchResultInteractionListener {
    init {
        getMoviesGenres()
        getSeriesGenres()
        getMovies()
        getSeries()
        getActors()
    }

    override fun selectTap(tabIndex: Int) {
        safeExecute {
            updateState { it.copy(selectedTab = SearchTab.entries[tabIndex]) }
            when (state.value.selectedTab) {
                SearchTab.MOVIES -> updateState { it.copy(selectedPosters = state.value.moviesPosters) }
                SearchTab.SERIES -> updateState { it.copy(selectedPosters = state.value.seriesPosters) }
                SearchTab.ACTORS -> {}
            }
        }
    }

    override fun changeView(isGrid: Boolean) {
        safeExecute { updateState { it.copy(isGridSelected = isGrid) } }
    }

    private fun getMoviesGenres() {
        safeExecute {
            getMoviesGenresUseCase().let { genres ->
                updateState { it.copy(moviesGenres = genres.map { genre -> genre.toUi() }) }
            }
        }
    }

    private fun getSeriesGenres() {
        safeExecute {
            getSeriesGenresUseCase().let { genres ->
                updateState { it.copy(seriesGenres = genres.map { genre -> genre.toUi() }) }
            }
        }
    }

    private fun getMovies() {
        safeExecute {
            searchMovieByName(movieName = query).let { movies ->
                val moviesPosters = movies.map { movie -> movie.toPoster(state.value.moviesGenres) }
                updateState {
                    it.copy(
                        selectedPosters = moviesPosters,
                        moviesPosters = moviesPosters
                    )
                }
            }
        }
    }

    private fun getSeries() {
        safeExecute {
            searchSeriesByName(seriesName = query).let { series ->
                updateState {
                    it.copy(seriesPosters = series.map { series ->
                        series.toPoster(
                            state.value.seriesGenres
                        )
                    })
                }
            }
        }
    }

    private fun getActors() {
        val pagingFlow = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchPeoplePagingSource(query, searchPeopleByName) }
        ).flow.cachedIn(viewModelScope)

        updateState { it.copy(actors = pagingFlow) }
    }

}