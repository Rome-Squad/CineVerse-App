package com.giraffe.profile.screens.ratings

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetRatedMoviesUseCase
import com.giraffe.profile.base.BaseViewModel
import com.giraffe.profile.model.RatedPoster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val getRatedMoviesUseCase: GetRatedMoviesUseCase,
) : BaseViewModel<RatingScreenState, RatingEffect>(RatingScreenState()),
    RatingInteractionListener {

    init {

        onTabSelected(0)
    }

    private fun getRatedMovies() {
        safeExecute {
            getRatedMoviesUseCase()
        }
    }

    private fun onGetRatedMoviesSuccess(moviesMap: Map<Float, Movie>) {
        val ratedMovies = moviesMap.entries
            .map {
                RatedPoster.fromMovie(
                    movie = it.value,
                    rating = it.key,
                    genres = state.value.movieGenres
                )
            }

        if (state.value.selectedTabIndex == 0) {
            updateState {
                it.copy(
                    selectedPosters = ratedMovies,
                )
            }
        }
    }

    private fun onGetRatedMoviesFailure(error: Throwable) {
        sendEffect(RatingEffect.ShowError(mapErrorToResource(error)))
    }

    override fun onPosterClick(poster: Poster) {
        if (poster.mediaTypeOfPoster == Poster.Type.MOVIE.value) {
            sendEffect(RatingEffect.NavigateToMovieDetails(poster.id))
        }

        if (poster.mediaTypeOfPoster == Poster.Type.SERIES.value) {
            sendEffect(RatingEffect.NavigateToSeriesDetails(poster.id))
        }

    }

    override fun onCloseTipClick() {
        updateState { it.copy(isTipVisible = false) }
    }

    override fun onBackClick() {
        sendEffect(RatingEffect.NavigateBack)
    }

    override fun onTabSelected(tabIndex: Int) {
        updateState {
            it.copy(
                selectedTabIndex = tabIndex,
                selectedPosters = if (tabIndex == 0) it.moviesPosters else it.seriesPosters
            )
        }
    }
}