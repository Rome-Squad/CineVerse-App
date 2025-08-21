package com.giraffe.presentation.details.screens.recommended.movie

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
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetRecommendedMoviesUseCase
import com.giraffe.media.movie.usecase.genre.ObserveMoviesGenresUseCase
import com.giraffe.presentation.details.base.BasePagingSource
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.RecommendedMovieRoute
import com.giraffe.presentation.details.utils.toPoster
import com.giraffe.presentation.details.utils.toUi
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.giraffe.user.exception.NoInternetException as UserNoInternetException

@HiltViewModel
class RecommendedMoviesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecommendedMovies: GetRecommendedMoviesUseCase,
    private val observeMoviesGenresUseCase: ObserveMoviesGenresUseCase,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase

) : BaseViewModel<RecommendedMoviesScreenState, RecommendedMoviesEffect>(
    RecommendedMoviesScreenState(

        movieId = savedStateHandle.toRoute<RecommendedMovieRoute>().movieId,
        movieTitle = savedStateHandle.toRoute<RecommendedMovieRoute>().title
    )
),
    RecommendedInteractionListener {

    init {
        observeContentPreference()
        getMoviesGenres()
    }

    private fun getMoviesGenres() {
        safeCollect(
            onEmitNewValue = ::onGetMoviesGenresSuccess,
            onError = ::onError,
            block = observeMoviesGenresUseCase::invoke
        )
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                movieGenres = genres
            )
        }

        state.value.movieId?.let {
            getRecommendedMovies(it)
        }
    }


    private fun getRecommendedMovies(
        movieId: Int
    ) {

        safeExecute(
            onSuccess = ::onGetRecommendedMoviesSuccess,
            onError = ::onError
        ) {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = 15,
                    prefetchDistance = 5,
                    initialLoadSize = 15
                )
            ) {
                BasePagingSource(
                    onError = ::onError
                ) { page ->
                    getRecommendedMovies(movieId, page)
                }
            }

            pager
                .flow
                .cachedIn(viewModelScope)
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    PagingData.empty()
                )
        }
    }

    private fun onGetRecommendedMoviesSuccess(moviesFlow: Flow<PagingData<Movie>>) {
        val movieUiFlow = moviesFlow.map { pagingData ->
            pagingData.map { it.toUi(state.value.movieGenres).toPoster() }
        }

        updateState {
            it.copy(
                recommendedMoviesFlow = movieUiFlow,
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    override fun onMovieClick(movieId: Int) {
        sendEffect(RecommendedMoviesEffect.NavigateToMovieDetails(movieId))

    }

    override fun onBackClick() {
        sendEffect(RecommendedMoviesEffect.NavigateBack)
    }

    override fun onRetryClick() {
        getMoviesGenres()
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
        val isNoInternet = error is NoInternetException ||
                error is UserNoInternetException

        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = isNoInternet || it.isNoInternet
            )
        }

        sendEffect(RecommendedMoviesEffect.ShowError(error))
    }
}