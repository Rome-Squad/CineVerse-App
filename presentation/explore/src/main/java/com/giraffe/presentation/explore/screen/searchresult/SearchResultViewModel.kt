package com.giraffe.presentation.explore.screen.searchresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.mediaMember.entity.CastMember
import com.giraffe.media.mediaMember.usecase.GetMediaMembersByNameUseCase
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetMoviesByNameUseCase
import com.giraffe.media.movie.usecase.genre.ObserveMoviesGenresUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesByNameUseCase
import com.giraffe.media.series.usecase.genre.ObserveSeriesGenresUseCase
import com.giraffe.presentation.explore.base.BaseViewModel
import com.giraffe.presentation.explore.navigation.routes.SearchResultRoute
import com.giraffe.presentation.explore.screen.discover.SearchTab
import com.giraffe.presentation.explore.util.BasePagingSource
import com.giraffe.presentation.explore.util.toPoster
import com.giraffe.presentation.explore.util.toUi
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val getMoviesByNameUseCase: GetMoviesByNameUseCase,
    private val getSeriesByName: GetSeriesByNameUseCase,
    private val searchPeopleByName: GetMediaMembersByNameUseCase,
    private val observeMoviesGenresUseCase: ObserveMoviesGenresUseCase,
    private val observeSeriesGenresUseCase: ObserveSeriesGenresUseCase,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SearchResultScreenState, SearchResultEffect>(
    SearchResultScreenState(query = savedStateHandle.toRoute<SearchResultRoute>().query),
), SearchResultInteractionListener {

    private var movieJob: Job? = null
    private var seriesJob: Job? = null
    private var actorsJob: Job? = null

    init {
        updateState { it.copy(isLoading = true) }
        observeContentPreference()
        getMoviesGenres()
        getSeriesGenres()
        getActors()
        viewModelScope.launch {
            movieJob?.join()
            seriesJob?.join()
            actorsJob?.join()
            updateState {
                it.copy(isLoading = false)
            }
        }
    }

    override fun selectTap(tabIndex: Int) {
        updateState {
            it.copy(
                selectedTab = SearchTab.entries[tabIndex],
                isLoading = true
            )
        }
        when (state.value.selectedTab) {
            SearchTab.MOVIES -> updateState {
                it.copy(
                    selectedPosters = state.value.moviesPosters,
                    isLoading = false
                )
            }

            SearchTab.SERIES -> updateState {
                it.copy(
                    selectedPosters = state.value.seriesPosters,
                    isLoading = false
                )
            }

            SearchTab.ACTORS -> updateState {
                it.copy(
                    selectedPosters = state.value.actorsPosters,
                    isLoading = false
                )
            }
        }
    }

    override fun changeView(isGrid: Boolean) {
        updateState {
            it.copy(
                isGridSelected = isGrid,
            )
        }
    }

    override fun onRetryClick() {
        getMoviesGenres()
        getSeriesGenres()
        getActors()
    }

    override fun onBackClick() {
        sendEffect(SearchResultEffect.NavigateBack)
    }

    override fun onPosterClick(mediaId: Int, selectedTab: SearchTab) {
        when (selectedTab) {
            SearchTab.MOVIES -> sendEffect(SearchResultEffect.NavigateToMovieDetail(mediaId))
            SearchTab.SERIES -> sendEffect(SearchResultEffect.NavigateToSeriesDetail(mediaId))
            SearchTab.ACTORS -> sendEffect(SearchResultEffect.NavigateToCastDetails(mediaId))
        }
    }

    override fun onRecordingClick() {
        sendEffect(SearchResultEffect.NavigateToSearchScreen)
    }


    private fun getMoviesGenres() {
        safeCollect(
            onEmitNewValue = ::getMoviesGenresSuccess,
            onError = ::onError.also { getMovies() },
            block = observeMoviesGenresUseCase::invoke
        )
    }

    private fun getMoviesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                moviesGenres = genres.map(Genre::toUi),
            )
        }
        getMovies()
    }

    private fun getSeriesGenres() {
        safeCollect(
            onEmitNewValue = ::getSeriesGenresSuccess,
            onError = ::onError.also { getSeries() },
            block = observeSeriesGenresUseCase::invoke,
        )
    }

    private fun getSeriesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                seriesGenres = genres.map(Genre::toUi),
            )
        }
        getSeries()
    }

    private fun getMovies() {
        movieJob = safeExecute(
            onSuccess = ::onGetMoviesSuccess,
            onError = ::onError,
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> getMoviesByNameUseCase(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetMoviesSuccess(moviesFlow: Flow<PagingData<Movie>>) {
        moviesFlow.map { it.map { movie -> movie.toPoster(state.value.moviesGenres) } }
            .let { posters ->
                updateState {
                    it.copy(
                        moviesPosters = posters,
                        selectedPosters = state.value.moviesPosters,
                    )
                }
            }
    }

    private fun getSeries() {
        seriesJob = safeExecute(
            onSuccess = ::onGetSeriesSuccess,
            onError = ::onError
        ) {
            Pager(PagingConfig(pageSize = 15, prefetchDistance = 5, initialLoadSize = 15)) {
                BasePagingSource { page -> getSeriesByName(state.value.query, page) }
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun onGetSeriesSuccess(seriesFlow: Flow<PagingData<Series>>) {
        seriesFlow.map { it.map { series -> series.toPoster(state.value.seriesGenres) } }
            .let { posters ->
                updateState {
                    it.copy(
                        seriesPosters = posters,
                    )
                }
            }
    }

    private fun getActors() {
        actorsJob = safeExecute(
            onSuccess = ::onGetActorsSuccess,
            onError = ::onError
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
                )
            }
        }
    }

    private fun observeContentPreference() {
        safeCollect(
            onEmitNewValue = { preference ->
                updateState { it.copy(contentPreference = preference) }
            },
            block = getContentPreferenceUseCase::invoke
        )
    }

    private fun onError(error: Throwable) {
        updateState {
            it.copy(
                isNoInternet = error is NoInternetException,
                isLoading = false
            )
        }
        sendEffect(SearchResultEffect.ShowError(error))
    }
}