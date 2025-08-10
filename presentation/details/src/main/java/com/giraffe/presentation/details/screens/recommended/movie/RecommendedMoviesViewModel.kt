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
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movie.usecase.GetRecommendedMovieUseCase
import com.giraffe.presentation.details.base.BasePagingSource
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.RecommendedMovieRoute
import com.giraffe.presentation.details.utils.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecommendedMoviesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecommendedMovies: GetRecommendedMovieUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
) : BaseViewModel<RecommendedMoviesScreenState, RecommendedMoviesEffect>(
    RecommendedMoviesScreenState(

        movieId = savedStateHandle.toRoute<RecommendedMovieRoute>().movieId,
        movieTitle = savedStateHandle.toRoute<RecommendedMovieRoute>().title
    )
),
    RecommendedInteractionListener {

    init {
        getMoviesGenres()
    }

    private fun getMoviesGenres() {
        safeExecute(
            onSuccess = ::onGetMoviesGenresSuccess,
            onError = ::onGetMoviesGenresFailure
        ) {
            getMoviesGenresUseCase()
        }
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                isLoading = false,
                movieGenres = genres
            )
        }

        state.value.movieId?.let {
            getRecommendedMovies(it)
        }
    }

    private fun onGetMoviesGenresFailure(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false
            )
        }

        sendEffect(RecommendedMoviesEffect.ShowError(error))
    }


    private fun getRecommendedMovies(
        movieId: Int
    ) {

        safeExecute(
            onSuccess = ::onGetRecommendedMoviesSuccess,
            onError = ::onGetRecommendedMoviesError
        ) {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = 15,
                    prefetchDistance = 5,
                    initialLoadSize = 15
                )
            ) {
                BasePagingSource { page ->
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
            pagingData.map { it.toUi(state.value.movieGenres) }
        }

        updateState {
            it.copy(
                recommendedMoviesFlow = movieUiFlow,
                isLoading = false
            )
        }
    }

    private fun onGetRecommendedMoviesError(error: Throwable) {
        sendEffect(RecommendedMoviesEffect.ShowError(error))
    }

    override fun navigateToMovieDetailsScreen(movieId: Int) {
        sendEffect(RecommendedMoviesEffect.NavigateToMovieDetails(movieId))

    }

}