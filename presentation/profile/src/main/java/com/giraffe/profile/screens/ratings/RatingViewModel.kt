package com.giraffe.profile.screens.ratings

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.DeleteMovieRatingUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.GetRatedMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.DeleteSeriesRatingUseCase
import com.giraffe.media.series.usecase.GetRatedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.profile.base.BaseViewModel
import com.giraffe.profile.model.RatedPoster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val getRatedMoviesUseCase: GetRatedMoviesUseCase,
    private val getRatedSeriesUseCase: GetRatedSeriesUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    private val deleteMovieRatingUseCase: DeleteMovieRatingUseCase,
    private val deleteSeriesRatingUseCase: DeleteSeriesRatingUseCase
) : BaseViewModel<RatingScreenState, RatingEffect>(RatingScreenState()),
    RatingInteractionListener {

    init {
        getGenres()
    }

    private fun getGenres() {
        safeExecute(
            onSuccess = ::onGetMoviesGenresSuccess,
            onError = ::onGetMoviesGenresFailure,
            block = getMoviesGenresUseCase::invoke
        )
        safeExecute(
            onSuccess = ::onGetSeriesGenresSuccess,
            onError = ::onGetSeriesGenresFailure,
            block = getSeriesGenresUseCase::invoke
        )
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(isLoading = false, movieGenres = genres) }
        safeExecute(
            onSuccess = ::onGetRatedMoviesSuccess,
            onError = ::onGetRatedMoviesFailure,
            block = getRatedMoviesUseCase::invoke
        )
    }

    private fun onGetMoviesGenresFailure(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(RatingEffect.ShowError(mapErrorToResource(error)))
    }

    private fun onGetSeriesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(isLoading = false, seriesGenres = genres) }
        safeExecute(
            onSuccess = ::onGetRatedSeriesSuccess,
            onError = ::onGetRatedSeriesFailure,
            block = getRatedSeriesUseCase::invoke
        )
    }

    private fun onGetSeriesGenresFailure(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(RatingEffect.ShowError(mapErrorToResource(error)))
    }

    private fun onGetRatedMoviesSuccess(ratedMovies: Map<Float, Movie>) {
        val ratedMovies = ratedMovies.entries
            .map {
                RatedPoster.fromMovie(
                    movie = it.value,
                    rating = it.key,
                    genres = state.value.movieGenres
                )
            }
        updateState { it.copy(moviesPosters = ratedMovies) }
        if (state.value.selectedTabIndex == 0) updateState { it.copy(selectedPosters = ratedMovies) }
    }

    private fun onGetRatedMoviesFailure(error: Throwable) {
        sendEffect(RatingEffect.ShowError(mapErrorToResource(error)))
    }

    private fun onGetRatedSeriesSuccess(ratedSeries: Map<Float, Series>) {
        val ratedSeries = ratedSeries.entries
            .map {
                RatedPoster.fromSeries(
                    series = it.value,
                    rating = it.key,
                    genres = state.value.seriesGenres
                )
            }
        updateState { it.copy(seriesPosters = ratedSeries) }
        if (state.value.selectedTabIndex != 0) updateState { it.copy(selectedPosters = ratedSeries) }
    }

    private fun onGetRatedSeriesFailure(error: Throwable) {
        sendEffect(RatingEffect.ShowError(mapErrorToResource(error)))
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


    override fun onPosterClick(poster: Poster) {
        if (poster.mediaTypeOfPoster == Poster.Type.MOVIE.value) {
            sendEffect(RatingEffect.NavigateToMovieDetails(poster.id))
        }

        if (poster.mediaTypeOfPoster == Poster.Type.SERIES.value) {
            sendEffect(RatingEffect.NavigateToSeriesDetails(poster.id))
        }

    }

    override fun onDeleteRatedPosterClick(poster: Poster) {
        if (poster.mediaTypeOfPoster == Poster.Type.MOVIE.value) {
            deleteMovieRating(poster.id)
        }

        if (poster.mediaTypeOfPoster == Poster.Type.SERIES.value) {
            deleteSeriesRating(poster.id)
        }
    }

    private fun deleteMovieRating(movieId: Int) {
        safeExecute(
            onSuccess = { onDeleteMovieRatingSuccess() },
            onError = ::onMovieRatingDeleteFailure
        ) {
            deleteMovieRatingUseCase(movieId)
        }
    }

    private fun onDeleteMovieRatingSuccess() {
        safeExecute(
            onSuccess = ::onGetRatedMoviesSuccess,
            onError = ::onGetRatedMoviesFailure,
            block = getRatedMoviesUseCase::invoke
        )
    }

    private fun onMovieRatingDeleteFailure(error: Throwable) {
        sendEffect(RatingEffect.ShowError(mapErrorToResource(error)))
    }

    private fun deleteSeriesRating(seriesId: Int) {
        safeExecute(
            onSuccess = { onDeleteSeriesRatingSuccess() },
            onError = ::onSeriesRatingDeleteFailure
        ) {
            deleteSeriesRatingUseCase(seriesId)
        }
    }

    private fun onDeleteSeriesRatingSuccess() {
        safeExecute(
            onSuccess = ::onGetRatedSeriesSuccess,
            onError = ::onGetRatedSeriesFailure,
            block = getRatedSeriesUseCase::invoke
        )
    }

    private fun onSeriesRatingDeleteFailure(error: Throwable) {
        sendEffect(RatingEffect.ShowError(mapErrorToResource(error)))
    }
}