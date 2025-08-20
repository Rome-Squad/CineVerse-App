package com.giraffe.presentation.profile.screens.history

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.genre.ObserveMoviesGenresUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.DeleteRecentlyViewedMovieByIdUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.DeleteSeriesUseCase
import com.giraffe.media.series.usecase.genre.ObserveSeriesGenresUseCase
import com.giraffe.media.series.usecase.recentlyViewed.ObserveRecentlyViewedSeriesUseCase
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.uimodel.Poster
import com.giraffe.presentation.profile.utils.toPoster
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
    private val observeRecentlyViewedSeriesUseCase: ObserveRecentlyViewedSeriesUseCase,
    private val observeMoviesGenresUseCase: ObserveMoviesGenresUseCase,
    private val observeSeriesGenresUseCase: ObserveSeriesGenresUseCase,
    private val deleteRecentlyViewedMovieByIdUseCase: DeleteRecentlyViewedMovieByIdUseCase,
    private val deleteSeriesUseCase: DeleteSeriesUseCase
) :
    BaseViewModel<HistoryScreenState, HistoryEffect>(HistoryScreenState()),
    HistoryInteractionListener {

    init {
        getGenres()
    }

    private fun getGenres() {
        safeCollect(
            onEmitNewValue = ::onGetMoviesGenresSuccess,
            onError = ::onFailure.also { getRecentViewedMovies() },
            block = observeMoviesGenresUseCase::invoke
        )
        safeCollect(
            onEmitNewValue = ::onGetSeriesGenresSuccess,
            onError = ::onFailure.also { getRecentViewedSeries() },
            block = observeSeriesGenresUseCase::invoke
        )
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(moviesGenres = genres) }
        getRecentViewedMovies()
    }

    private fun onGetSeriesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(seriesGenres = genres) }
        getRecentViewedSeries()
    }

    private fun getRecentViewedMovies() {
        safeCollect(
            onEmitNewValue = ::onGetRecentMoviesSuccess,
            onError = ::onFailure,
            block = observeRecentlyViewedMoviesUseCase::invoke
        )
    }

    private fun getRecentViewedSeries() {
        safeCollect(
            onEmitNewValue = ::onGetRecentSeriesSuccess,
            onError = ::onFailure,
            block = observeRecentlyViewedSeriesUseCase::invoke
        )
    }

    private fun onGetRecentMoviesSuccess(moviesList: List<Movie>) {
        updateMediaList(moviesList.map { movie -> movie.toPoster(state.value.moviesGenres.filter { it.id in movie.genresID }) })
    }

    private fun onGetRecentSeriesSuccess(seriesList: List<Series>) {
        updateMediaList(seriesList.map { series -> series.toPoster(state.value.seriesGenres.filter { it.id in series.genreIDs }) })
    }

    private fun updateMediaList(newMediaList: List<Poster>) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = false,
                mediaList = (it.mediaList + newMediaList)
                    .distinctBy { poster -> poster.id }
                    .sortedByDescending { poster -> poster.recentViewedAt }
            )
        }
    }

    private fun onFailure(error: Throwable, isNoInternet: Boolean) {
        updateState { it.copy(isLoading = false, isNoInternet = isNoInternet) }
        sendEffect(HistoryEffect.ShowError(error))
    }

    override fun onDeleteClicked(id: Int, mediaType: String) {
        safeExecute(
            onError = ::onFailure,
            onSuccess = {
                updateState {
                    it.copy(
                        mediaList = state.value.mediaList.filterNot { media -> media.id == id },
                        swipedPosterId = null,
                        isSwiped = false
                    )
                }
            }
        ) {
            if (mediaType == "movie") {
                deleteRecentlyViewedMovieByIdUseCase(id)
            } else if (mediaType == "series") {
                deleteSeriesUseCase(id)
            }
        }

    }

    override fun onCloseClicked() {
        updateState { it.copy(isTipVisible = false) }
    }

    override fun onMediaClicked(mediaId: Int, mediaType: String) {
        when (mediaType) {
            "movie" -> sendEffect(HistoryEffect.NavigateToMovieDetails(mediaId))
            "series" -> sendEffect(HistoryEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun onBackClick() {
        sendEffect(HistoryEffect.NavigateBack)
    }

    override fun navigateToExploreScreen() {
        sendEffect(HistoryEffect.NavigateToExploreScreen)
    }
}