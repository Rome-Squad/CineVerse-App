package com.giraffe.presentation.explore.screen.discover

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesByGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.presentation.explore.base.BaseViewModel
import com.giraffe.presentation.explore.model.GenreUi
import com.giraffe.presentation.explore.screen.discover.DiscoverEffect.NavigateToMovieDetails
import com.giraffe.presentation.explore.screen.discover.DiscoverEffect.NavigateToSeriesDetails
import com.giraffe.presentation.explore.util.BasePagingSource
import com.giraffe.presentation.explore.util.mapExceptionToStringRes
import com.giraffe.presentation.explore.util.toPoster
import com.giraffe.presentation.explore.util.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,
    private val getSeriesByGenresUseCase: GetSeriesByGenresUseCase,
) : BaseViewModel<DiscoverScreenState, DiscoverEffect>(DiscoverScreenState()),
    DiscoverInteractionListener {

    init {
        getMovieGenres()
        getSeriesGenres()
    }


    private fun getMovieGenres() {
        safeExecute(
            onSuccess = ::onGetMoviesGenresSuccess,
            onError = ::onError,
            block = { getMoviesGenresUseCase() }
        )
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                isLoading = false,
                moviesGenres = genres.map(Genre::toUi)
            )
        }
        getMoviesByGenre()
    }

    private fun getSeriesGenres() {
        safeExecute(
            onSuccess = ::getSeriesGenresSuccess,
            onError = ::onError,
            block = { getSeriesGenresUseCase() }
        )
    }

    private fun getSeriesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                isLoading = false,
                seriesGenres = genres.map(Genre::toUi)
            )
        }
        getSeriesByGenre()
    }

    override fun getMoviesByGenre(genreId: Int) {
        safeExecute(
            onError = ::onError,
            onSuccess = ::onGetMovieGenresSuccess
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource(
                    error = ::onError,
                ) { page -> getMoviesByGenresUseCase(genreId, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetMovieGenresSuccess(moviesFlow: Flow<PagingData<Movie>>) {
        val postersFlow = moviesFlow.map { pagingData ->
            pagingData.map { movie -> movie.toPoster(state.value.moviesGenres) }
        }

        updateState { it.copy(moviesPosters = postersFlow) }
        if (state.value.selectedTab == SearchTab.MOVIES) {
            updateState { it.copy(selectedPosters = postersFlow) }
        }
    }

    override fun getSeriesByGenre(genreId: Int) {
        safeExecute(
            onError = ::onError,
            onSuccess = ::onGetSeriesGenresSuccess
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource(
                    error = ::onError,
                ) { page -> getSeriesByGenresUseCase(genreId, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetSeriesGenresSuccess(seriesFlow: Flow<PagingData<Series>>) {
        val postersFlow = seriesFlow.map { pagingData ->
            pagingData.map { series -> series.toPoster(state.value.seriesGenres) }
        }

        updateState { it.copy(seriesPosters = postersFlow) }
        if (state.value.selectedTab == SearchTab.SERIES) {
            updateState { it.copy(selectedPosters = postersFlow) }
        }
    }

    override fun onViewChanged(isGrid: Boolean) {
        updateState { it.copy(isGridSelected = isGrid) }
    }

    override fun onGenreSelected(genre: GenreUi) {
        if (state.value.selectedTab == SearchTab.MOVIES) {
            updateState { it.copy(selectedGenre = genre, selectedMovieGenre = genre) }
            getMoviesByGenre(genre.id)
        } else {
            updateState { it.copy(selectedGenre = genre, selectedSeriesGenre = genre) }
            getSeriesByGenre(genre.id)
        }
    }

    override fun onPosterClick(mediaId: Int, searchTab: SearchTab) {
        when (searchTab) {
            SearchTab.MOVIES -> sendEffect(NavigateToMovieDetails(mediaId))
            SearchTab.SERIES -> sendEffect(NavigateToSeriesDetails(mediaId))
            SearchTab.ACTORS -> {}
        }
    }

    override fun onSearchClick() {
        sendEffect(DiscoverEffect.NavigateToSearchScreen)
    }

    override fun onTabSelected(tabIndex: Int) {
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


    private fun onError(error: Throwable) {
        updateState {
            it.copy(
                errorMessageRes = mapExceptionToStringRes(error),
                isNoInternet = error is NoInternetException,
                isLoading = false
            )
        }
        sendEffect(DiscoverEffect.Error(error))
    }
}