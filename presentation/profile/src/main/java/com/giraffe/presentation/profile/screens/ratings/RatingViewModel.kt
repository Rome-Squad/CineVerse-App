package com.giraffe.presentation.profile.screens.ratings

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
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.model.RatedPoster
import com.giraffe.presentation.profile.utils.toRatedPoster
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
            onError = ::onFailure,
            block = getMoviesGenresUseCase::invoke
        )
        safeExecute(
            onSuccess = ::onGetSeriesGenresSuccess,
            onError = ::onFailure,
            block = getSeriesGenresUseCase::invoke
        )
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(isLoading = false, movieGenres = genres) }
        safeExecute(
            onSuccess = ::onGetRatedMoviesSuccess,
            onError = ::onFailure,
            block = getRatedMoviesUseCase::invoke
        )
    }

    private fun onFailure(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(RatingEffect.ShowError(error))
    }

    private fun onGetSeriesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(isLoading = false, seriesGenres = genres) }
        safeExecute(
            onSuccess = ::onGetRatedSeriesSuccess,
            onError = ::onFailure,
            block = getRatedSeriesUseCase::invoke
        )
    }

    private fun onGetRatedMoviesSuccess(ratedMovies: List<Movie>) {
        val ratedMovies =
            ratedMovies.map { it.toRatedPoster(state.value.movieGenres.filter { genres -> genres.id in it.genresID }) }
        updateState { it.copy(moviesPosters = ratedMovies) }
        if (state.value.selectedTabIndex == 0) updateState { it.copy(selectedPosters = ratedMovies) }
    }

    private fun onGetRatedSeriesSuccess(ratedSeries: List<Series>) {
        val ratedSeries =
            ratedSeries.map { it.toRatedPoster(state.value.seriesGenres.filter { genres -> genres.id in it.genreIDs }) }
        updateState { it.copy(seriesPosters = ratedSeries) }
        if (state.value.selectedTabIndex != 0) updateState { it.copy(selectedPosters = ratedSeries) }
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

    override fun onPosterClick(ratedPoster: RatedPoster) {
        if (ratedPoster.poster.mediaTypeOfPoster == Poster.Type.MOVIE.value) {
            sendEffect(RatingEffect.NavigateToMovieDetails(ratedPoster.poster.id))
        }

        if (ratedPoster.poster.mediaTypeOfPoster == Poster.Type.SERIES.value) {
            sendEffect(RatingEffect.NavigateToSeriesDetails(ratedPoster.poster.id))
        }

    }

    override fun onDeleteRatedPosterClick(ratedPoster: RatedPoster) {
        if (ratedPoster.poster.mediaTypeOfPoster == Poster.Type.MOVIE.value) {
            deleteMovieRating(ratedPoster)
        }

        if (ratedPoster.poster.mediaTypeOfPoster == Poster.Type.SERIES.value) {
            deleteSeriesRating(ratedPoster)
        }
    }

    private fun deleteMovieRating(ratedPoster: RatedPoster) {
        safeExecute(
            onSuccess = { onDeleteMovieRatingSuccess(ratedPoster) },
            onError = ::onFailure
        ) {
            deleteMovieRatingUseCase(ratedPoster.poster.id)
        }
    }

    private fun onDeleteMovieRatingSuccess(ratedPoster: RatedPoster) {
        (state.value.moviesPosters - ratedPoster).let { moviesPosters ->
            updateState { it.copy(moviesPosters = moviesPosters) }
            if (state.value.selectedTabIndex == 0) updateState { it.copy(selectedPosters = moviesPosters) }
        }
    }

    private fun deleteSeriesRating(ratedPoster: RatedPoster) {
        safeExecute(
            onSuccess = { onDeleteSeriesRatingSuccess(ratedPoster) },
            onError = ::onFailure
        ) {
            deleteSeriesRatingUseCase(ratedPoster.poster.id)
        }
    }

    private fun onDeleteSeriesRatingSuccess(ratedPoster: RatedPoster) {
        (state.value.seriesPosters - ratedPoster).let { seriesPosters ->
            updateState { it.copy(seriesPosters = seriesPosters) }
            if (state.value.selectedTabIndex != 0) updateState { it.copy(selectedPosters = seriesPosters) }
        }
    }
}