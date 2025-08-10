package com.giraffe.explore.screen.searchresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.explore.base.BaseViewModel
import com.giraffe.explore.screen.discover.SearchTab
import com.giraffe.explore.util.BasePagingSource
import com.giraffe.explore.util.toPoster
import com.giraffe.explore.util.toUi
import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.usecase.SearchMediaMembersByNameUseCase
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movie.usecase.SearchMovieByNameUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesByNameUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchMovieByName: SearchMovieByNameUseCase,
    private val searchSeriesByName: GetSeriesByNameUseCase,
    private val searchPeopleByName: SearchMediaMembersByNameUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SearchResultScreenState>(
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


    private fun getMoviesGenres() {
        safeExecute(
            onSuccess = { genres ->
                updateState { it.copy(moviesGenres = genres.map { genre -> genre.toUi() }) }
            },
            onError = ::onFail,
            block = getMoviesGenresUseCase::invoke,
        )
    }

    private fun getSeriesGenres() {
        safeExecute(
            onSuccess = { genres ->
                updateState { it.copy(seriesGenres = genres.map { genre -> genre.toUi() }) }
            },
            onError = ::onFail,
            block = getSeriesGenresUseCase::invoke,
        )
    }

    private fun getMovies() {
        safeExecute(
            onSuccess = ::onGetMoviesSuccess,
            onError = ::onFail,
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
                    errorMessage = null,
                    isNetworkError = false
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
            onError = ::onFail
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> searchSeriesByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetSeriesSuccess(seriesFlow: Flow<PagingData<Series>>) {
        seriesFlow.map { series -> series.map(Series::toPoster) }.let { posters ->
            updateState {
                it.copy(
                    seriesPosters = posters,
                    errorMessage = null,
                    isNetworkError = false
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
            onError = ::onFail
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> searchPeopleByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetActorsSuccess(actorsFlow: Flow<PagingData<CastMember>>) {
        actorsFlow.map { actors -> actors.map(CastMember::toPoster) }.let { posters ->
            updateState {
                it.copy(
                    actorsPosters = posters,
                    errorMessage = null,
                    isNetworkError = false
                )
            }
            if (state.value.selectedTab == SearchTab.ACTORS) updateState {
                it.copy(selectedPosters = posters)
            }
        }
    }

    private fun onFail(errorMsgRes: Int, isNetworkError: Boolean) {
        updateState {
            it.copy(
                errorMessage = errorMsgRes,
                isNetworkError = isNetworkError
            )
        }
    }
}