package com.giraffe.home.screen.show_more.upcoming_movies

import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.screen.home.MediaType
import com.giraffe.home.screen.show_more.ShowMoreEffect
import com.giraffe.home.screen.show_more.ShowMoreInteractionListener
import com.giraffe.home.screen.show_more.ShowMoreUiState
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : BaseViewModel<ShowMoreUiState, ShowMoreEffect>(initialState = ShowMoreUiState()),
    ShowMoreInteractionListener {

    init {
        getUpcomingMovies()
    }

    private fun getUpcomingMovies() {
        safeExecute(
            onSuccess = ::getUpcomingMoviesSuccess,
            onError = ::onFail,
            block = { getUpcomingMoviesUseCase(page = 1) }
        )
    }

    private fun getUpcomingMoviesSuccess(movies: List<Movie>) {
        val upcomingMoviesUi = movies.map { movie ->
            movie.toPosterUi()
        }
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = upcomingMoviesUi,
            )
        }
    }

    private fun onFail(errorMsgRes: Int) {
        updateState { it.copy(isLoading = false, errorMsgRes = errorMsgRes) }
    }

    override fun onViewChanged(isGrid: Boolean) {
        updateState {
            it.copy(isListSelected = !isGrid)
        }
    }

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        sendEffect(ShowMoreEffect.NavigateToMovieDetails(mediaId))
    }
}