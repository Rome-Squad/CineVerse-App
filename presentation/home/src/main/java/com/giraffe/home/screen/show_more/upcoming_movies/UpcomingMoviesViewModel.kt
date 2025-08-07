package com.giraffe.home.screen.show_more.upcoming_movies

import com.giraffe.home.screen.show_more.BaseShowMoreViewModel
import com.giraffe.home.screen.show_more.ShowMoreInteractionListener
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : BaseShowMoreViewModel(),
    ShowMoreInteractionListener {

    init {
        loadData()
    }

    override fun loadData() {
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
}