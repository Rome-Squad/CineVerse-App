package com.giraffe.explore.screen.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.explore.util.exceptionHandler
import com.giraffe.explore.util.toPoster
import com.giraffe.explore.util.toUi
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesByGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val getMoviesGenres: GetMoviesGenresUseCase,
    private val getSeriesGenres: GetSeriesGenresUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,
    private val getSeriesByGenresUseCase: GetSeriesByGenresUseCase,
) : ViewModel(), DiscoverInteractionListener {
    private val _state = MutableStateFlow(DiscoverScreenState())
    val state: StateFlow<DiscoverScreenState> = _state.asStateFlow()
    private val exceptionHandler = exceptionHandler(_state)

    init {
        getGenres()
    }

    private fun getGenres() = safeExecute {
        val moviesGenres = getMoviesGenres().map { it.toUi() }
        val seriesGenres = getSeriesGenres().map { it.toUi() }
        _state.update {
            it.copy(
                selectedGenres = moviesGenres,
                moviesGenres = moviesGenres,
                seriesGenres = seriesGenres
            )
        }
        getMoviesByGenre()
        getSeriesByGenre()
    }

    override fun getMoviesByGenre(genreId: Int) = safeExecute {
        val movies =
            getMoviesByGenresUseCase(genreId).map { it.toPoster(_state.value.moviesGenres) }
        _state.update { it.copy(moviesPosters = movies) }
        if (_state.value.selectedTab == SearchTab.MOVIES) _state.update {
            it.copy(
                selectedPosters = movies
            )
        }
    }

    override fun getSeriesByGenre(genreId: Int) = safeExecute {
        val series =
            getSeriesByGenresUseCase(genreId).map { it.toPoster(_state.value.seriesGenres) }
        _state.update { it.copy(seriesPosters = series) }
        if (_state.value.selectedTab == SearchTab.SERIES) _state.update {
            it.copy(
                selectedPosters = series
            )
        }
    }

    override fun onViewChanged(isGrid: Boolean) = safeExecute {
        _state.update { it.copy(isGridSelected = isGrid) }
    }

    override fun onTabSelected(tabIndex: Int) = safeExecute {
        _state.update {
            it.copy(selectedTab = SearchTab.entries[tabIndex])
        }


        if (SearchTab.entries[tabIndex] == SearchTab.MOVIES) {
            _state.update {
                it.copy(
                    selectedGenre = _state.value.selectedMovieGenre,
                    selectedGenres = _state.value.moviesGenres,
                    selectedPosters = _state.value.moviesPosters
                )
            }
        } else {
            _state.update {
                it.copy(
                    selectedGenre = _state.value.selectedSeriesGenre,
                    selectedGenres = _state.value.seriesGenres,
                    selectedPosters = _state.value.seriesPosters
                )
            }
        }
    }

    override fun onGenreSelected(genre: GenreUi) = safeExecute {
        if (_state.value.selectedTab == SearchTab.MOVIES) {
            _state.update { it.copy(selectedGenre = genre, selectedMovieGenre = genre) }
            getMoviesByGenre(genre.id)
        } else {
            _state.update { it.copy(selectedGenre = genre, selectedSeriesGenre = genre) }
            getSeriesByGenre(genre.id)
        }
    }

    private fun safeExecute(bloc: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            bloc()
        }
    }
}