package com.giraffe.explore.screen.discover

import com.giraffe.explore.base.BaseViewModel
import com.giraffe.explore.util.toPoster
import com.giraffe.explore.util.toUi
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase

class DiscoverViewModel(
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
            getMoviesByGenresUseCase(genreId).map { it.toPoster(state.value.moviesGenres) }
                .let { movies ->
                    updateState { it.copy(moviesPosters = movies) }
                    if (state.value.selectedTab == SearchTab.MOVIES) updateState {
                        it.copy(
                            selectedPosters = movies
                        )
                    }
                }
        }
    }

    override fun getSeriesByGenre(genreId: Int) {
        safeExecute {
            getSeriesByGenresUseCase(genreId).map { it.toPoster(state.value.seriesGenres) }
                .let { series ->
                    updateState { it.copy(seriesPosters = series) }
                    if (state.value.selectedTab == SearchTab.SERIES) updateState {
                        it.copy(
                            selectedPosters = series
                        )
                    }
                }
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