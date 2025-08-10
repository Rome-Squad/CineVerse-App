package com.giraffe.presentation.explore.screen.searchresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movie.usecase.SearchMovieByNameUseCase
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesByNameUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.presentation.explore.base.BaseViewModel
import com.giraffe.presentation.explore.screen.discover.SearchTab
import com.giraffe.presentation.explore.util.BasePagingSource
import com.giraffe.presentation.explore.util.mapExceptionToStringRes
import com.giraffe.presentation.explore.util.toPoster
import com.giraffe.presentation.explore.util.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchMovieByName: SearchMovieByNameUseCase,
    private val getSeriesByName: GetSeriesByNameUseCase,
    private val searchPeopleByName: SearchPeopleByNameUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SearchResultScreenState, SearchResultEffect>(
    SearchResultScreenState(query = savedStateHandle.get<String>("query").orEmpty()),
), SearchResultInteractionListener {

    init {
        getMoviesGenres()
        getSeriesGenres()
        getMovies()
        getSeries()
        getActors()
    }

    override fun selectTap(tabIndex: Int) {
        updateState { it.copy(selectedTab = SearchTab.entries[tabIndex]) }
        when (state.value.selectedTab) {
            SearchTab.MOVIES -> updateState { it.copy(selectedPosters = state.value.moviesPosters) }
            SearchTab.SERIES -> updateState { it.copy(selectedPosters = state.value.seriesPosters) }
            SearchTab.ACTORS -> updateState { it.copy(selectedPosters = state.value.actorsPosters) }
        }
    }

    override fun changeView(isGrid: Boolean) {
        updateState { it.copy(isGridSelected = isGrid) }
    }

    override fun retry() {
        getMoviesGenres()
        getSeriesGenres()
        getMovies()
        getSeries()
        getActors()
    }

    override fun onBackClick() {
        sendEffect(SearchResultEffect.OnBackClick)
    }

    override fun onPosterClick(id: Int, searchTab: SearchTab) {
        when (searchTab) {
            SearchTab.MOVIES -> sendEffect(SearchResultEffect.NavigateToMovieDetail(id))
            SearchTab.SERIES -> sendEffect(SearchResultEffect.NavigateToSeriesDetail(id))
            SearchTab.ACTORS -> sendEffect(SearchResultEffect.NavigateToCastDetails(id))
        }
    }


    private fun getMoviesGenres() {
        safeExecute(
            onSuccess = { genres ->
                updateState { it.copy(moviesGenres = genres.map { genre -> genre.toUi() }) }
            },
            onError = ::onError,
            block = { getMoviesGenresUseCase.invoke() }
        )
    }

    private fun getSeriesGenres() {
        safeExecute(
            onSuccess = { genres ->
                updateState { it.copy(seriesGenres = genres.map { genre -> genre.toUi() }) }
            },
            onError = ::onError,
            block = { getSeriesGenresUseCase.invoke() },
        )
    }

    private fun getMovies() {
        safeExecute(
            onSuccess = ::onGetMoviesSuccess,
            onError = ::onError,
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> searchMovieByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetMoviesSuccess(moviesFlow: Flow<PagingData<Movie>>) {
        moviesFlow.map { it.map(Movie::toPoster) }.let { posters ->
            updateState {
                it.copy(
                    moviesPosters = posters,
                    errorMessageRes = null,
                    isNoInternet = false
                )
            }
            if (state.value.selectedTab == SearchTab.MOVIES) updateState {
                it.copy(selectedPosters = posters)
            }
        }
    }

    private fun getSeries() {
        safeExecute(
            onSuccess = ::onGetSeriesSuccess,
            onError = ::onError
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> getSeriesByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetSeriesSuccess(seriesFlow: Flow<PagingData<Series>>) {
        seriesFlow.map { series -> series.map(Series::toPoster) }.let { posters ->
            updateState {
                it.copy(
                    seriesPosters = posters,
                    errorMessageRes = null,
                    isNoInternet = false
                )
            }
            if (state.value.selectedTab == SearchTab.SERIES) updateState {
                it.copy(selectedPosters = posters)
            }
        }
    }

    private fun getActors() {
        safeExecute(
            onSuccess = ::onGetActorsSuccess,
            onError = ::onError
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> searchPeopleByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetActorsSuccess(actorsFlow: Flow<PagingData<Person>>) {
        actorsFlow.map { actors -> actors.map(Person::toPoster) }.let { posters ->
            updateState {
                it.copy(
                    actorsPosters = posters,
                    errorMessageRes = null,
                    isNoInternet = false
                )
            }
            if (state.value.selectedTab == SearchTab.ACTORS) updateState {
                it.copy(selectedPosters = posters)
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
        sendEffect(SearchResultEffect.Error(error))
    }
}