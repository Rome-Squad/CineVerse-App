package com.giraffe.explore.screen.discover

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.explore.base.BaseViewModel
import com.giraffe.explore.util.BasePagingSource
import com.giraffe.explore.util.toPoster
import com.giraffe.explore.util.toUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesByGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getMoviesGenres: GetMoviesGenresUseCase,
    private val getSeriesGenres: GetSeriesGenresUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,
    private val getSeriesByGenresUseCase: GetSeriesByGenresUseCase,
) : BaseViewModel<DiscoverScreenState>(DiscoverScreenState()), DiscoverInteractionListener {

    init {
        getGenres()
    }

    private fun getGenres() {
        safeExecute {
            val moviesGenres = getMoviesGenres().map { it.toUi() }
            val seriesGenres = getSeriesGenres().map { it.toUi() }
            updateState {
                it.copy(
                    selectedGenres = moviesGenres,
                    moviesGenres = moviesGenres,
                    seriesGenres = seriesGenres
                )
            }
            getMoviesByGenre()
            getSeriesByGenre()
        }
    }

    override fun getMoviesByGenre(genreId: Int) {
        safeExecute {
            val moviesFlow =
                Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                    BasePagingSource { page -> getMoviesByGenresUseCase(genreId, page) }
                }.flow.cachedIn(viewModelScope).map { it.map(Movie::toPoster) }
            updateState { it.copy(moviesPosters = moviesFlow) }
            if (state.value.selectedTab == SearchTab.MOVIES) updateState { it.copy(selectedPosters = moviesFlow) }
        }
    }

    override fun getSeriesByGenre(genreId: Int) {
        safeExecute {
            val seriesFlow =
                Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                    BasePagingSource { page -> getSeriesByGenresUseCase(genreId, page) }
                }.flow.cachedIn(viewModelScope).map { it.map(Series::toPoster) }
            updateState { it.copy(seriesPosters = seriesFlow) }
            if (state.value.selectedTab == SearchTab.SERIES) updateState { it.copy(selectedPosters = seriesFlow) }
        }
    }

    override fun onViewChanged(isGrid: Boolean) {
        safeExecute {
            updateState { it.copy(isGridSelected = isGrid) }
        }
    }

    override fun onTabSelected(tabIndex: Int) {
        safeExecute {
            updateState {
                it.copy(selectedTab = SearchTab.entries[tabIndex])
            }
            if (SearchTab.entries[tabIndex] == SearchTab.MOVIES) {
                updateState {
                    it.copy(
                        selectedGenre = state.value.selectedMovieGenre,
                        selectedGenres = state.value.moviesGenres,
                        selectedPosters = state.value.moviesPosters
                    )
                }
            } else {
                updateState {
                    it.copy(
                        selectedGenre = state.value.selectedSeriesGenre,
                        selectedGenres = state.value.seriesGenres,
                        selectedPosters = state.value.seriesPosters
                    )
                }
            }
        }
    }

    override fun onGenreSelected(genre: GenreUi) {
        safeExecute {
            if (state.value.selectedTab == SearchTab.MOVIES) {
                updateState { it.copy(selectedGenre = genre, selectedMovieGenre = genre) }
                getMoviesByGenre(genre.id)
            } else {
                updateState { it.copy(selectedGenre = genre, selectedSeriesGenre = genre) }
                getSeriesByGenre(genre.id)
            }
        }
    }
}