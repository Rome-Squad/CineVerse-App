package com.giraffe.explore.screen.searchresult

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.explore.base.BaseViewModel
import com.giraffe.explore.screen.discover.SearchTab
import com.giraffe.explore.util.BasePagingSource
import com.giraffe.explore.util.toPoster
import com.giraffe.explore.util.toUi
import com.giraffe.media.exception.MediaException
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.SearchMovieByNameUseCase
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.media.series.usecase.SearchSeriesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchMovieByName: SearchMovieByNameUseCase,
    private val searchSeriesByName: SearchSeriesByNameUseCase,
    private val searchPeopleByName: SearchPeopleByNameUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SearchResultScreenState>(
    SearchResultScreenState(query = savedStateHandle["query"] .orEmpty())
),
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
        safeExecute(
            onSuccess = { genres ->
                updateState { it.copy(moviesGenres = genres.map { genre -> genre.toUi() }) }
            },
            onError = ::onFail
        ) {
            getMoviesGenresUseCase()
        }
    }

    private fun getSeriesGenres() {
        safeExecute(
            onSuccess = { genres ->
                updateState { it.copy(seriesGenres = genres.map { genre -> genre.toUi() }) }
            },
            onError = ::onFail
        ) {
            getSeriesGenresUseCase()
        }
    }

    private fun getMovies() {
        safeExecute(
            onSuccess = { flow ->
                val moviesFlow = flow
                    .map { it.map(Movie::toPoster) }
                updateState { it.copy(selectedPosters = moviesFlow, moviesPosters = moviesFlow) }
            },
            onError = ::onFail
        ) {
            val moviesFlow = Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> searchMovieByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
            moviesFlow
        }
    }

    private fun getSeries() {
        safeExecute(
            onSuccess = { flow ->
                val seriesFlow = flow
                    .map { it.map(Series::toPoster) }
                updateState { it.copy(seriesPosters = seriesFlow) }
            },
            onError = ::onFail
        ) {
            val seriesFlow = Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> searchSeriesByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
            seriesFlow
        }
    }

    private fun getActors() {
        safeExecute(
            onSuccess = { flow ->
                val actorsFlow = flow
                    .map { it.map(Person::toUi) }
                updateState { it.copy(actors = actorsFlow, errorMessage = null, isNetworkError = true) }
            },
            onError = ::onFail
        ) {
            val actorsFlow = Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> searchPeopleByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
            actorsFlow
        }
    }


    private fun onFail(errorMsgRes: Int, isConnected: Boolean) =
        updateState { it.copy(errorMessage = errorMsgRes, isNetworkError = isConnected) }

}