package com.giraffe.home.screen.movies_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.screen.home.MediaType
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.exception.MediaException
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movies.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeriesUseCase: GetRecentlyReleasedSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getRecentlyViewedMoviesUseCase: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
    stateSavedStateHandle: SavedStateHandle
) : BaseViewModel<MoviesListUiState, MoviesListEffect>(initialState = MoviesListUiState()),
    MoviesListInteractionListener {
    private val sectionTitle = stateSavedStateHandle.toRoute<MoviesListRoute>().sectionTitle
    private val sectionType = stateSavedStateHandle.toRoute<MoviesListRoute>().sectionType
    private val collectionId = stateSavedStateHandle.toRoute<MoviesListRoute>().collectionId

    init {
        when (sectionType) {
            MovieSectionType.RECENTLY_VIEWED -> getRecentViewed()
            MovieSectionType.MATCHES_YOUR_VIBES -> getRecommendations()
            MovieSectionType.RECENTLY_RELEASED,
            MovieSectionType.TOP_RATED_TV_SHOWS,
            MovieSectionType.UPCOMING_MOVIES -> loadMoviesBySection(sectionType)

            else -> collectionId?.let { loadMoviesByGenres(it) }
        }
    }

    private fun loadMoviesByGenres(genreId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getMoviesByGenresUseCase(genreId = genreId, page = 1)
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        mediaList = movies.map(Movie::toPosterUi),
                        moviesListTitle = sectionTitle
                    )
                }
            } catch (e: MediaException) {
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }


    private fun loadMoviesBySection(sectionType: String) {
        safeExecute {
            updateState {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    mediaList = emptyList(),
                    moviesListTitle = sectionTitle
                )
            }
            val media = when (sectionType) {
                MovieSectionType.RECENTLY_RELEASED -> {
                    val recentMovies = getRecentlyReleasedMoviesUseCase(page = 1)
                    val recentSeries = getRecentlyReleasedSeriesUseCase(page = 1)
                    recentMovies.map { it.toPosterUi() } + recentSeries.map { it.toPosterUi() }
                }

                MovieSectionType.TOP_RATED_TV_SHOWS -> {
                    getTopRatedSeriesUseCase(page = 1).map { it.toPosterUi() }
                }

                MovieSectionType.UPCOMING_MOVIES -> {
                    getUpcomingMoviesUseCase(page = 1).map { it.toPosterUi() }
                }

                else -> emptyList()
            }
            updateState {
                it.copy(
                    isLoading = false,
                    errorMessage = null,
                    mediaList = media,
                )
            }
        }
    }

    private fun getRecentViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetRecentMoviesSuccess,
                onError = ::onFail,
                block = getRecentlyViewedMoviesUseCase::invoke
            ).join()
            safeExecute(
                onSuccess = ::onGetRecentSeriesSuccess,
                onError = ::onFail,
                block = getRecentlySeriesUseCase::invoke
            )
        }
    }

    private suspend fun onGetRecentMoviesSuccess(movies: Flow<List<Movie>>) {
        movies.collectLatest { moviesList ->
            updateState {
                it.copy(
                    isLoading = false,
                    errorMsgRes = null,
                    mediaList = (it.mediaList + moviesList.map(Movie::toPosterUi)).distinctBy { poster -> poster.id },
                )
            }
        }
    }

    private suspend fun onGetRecentSeriesSuccess(series: Flow<List<Series>>) {
        series.collectLatest { seriesList ->
            updateState {
                it.copy(
                    isLoading = false,
                    errorMsgRes = null,
                    mediaList = (it.mediaList + seriesList.map(Series::toPosterUi)).distinctBy { poster -> poster.id },
                )
            }
        }
    }

    private fun getRecommendations() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetRecommendedMoviesSuccess,
                onError = ::onFail,
                block = {
                    getRecentlyViewedMoviesUseCase().first().firstOrNull()?.id?.let { movieId ->
                        getRecommendedMovieUseCase(movieId = movieId, page = 1)
                    } ?: emptyList()
                }
            ).join()
            safeExecute(
                onSuccess = ::onGetRecommendedSeriesSuccess,
                onError = ::onFail,
                block = {
                    getRecentlySeriesUseCase().first().firstOrNull()?.id?.let { seriesId ->
                        getRecommendedSeriesUseCase(seriesId = seriesId, page = 1)
                    } ?: emptyList()
                }
            )
        }
    }

    private fun onGetRecommendedMoviesSuccess(recommendedMovies: List<Movie>) {
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = (it.mediaList + recommendedMovies.map(Movie::toPosterUi)).distinctBy { poster -> poster.id },
            )
        }
    }

    private fun onGetRecommendedSeriesSuccess(recommendedSeries: List<Series>) {
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = (it.mediaList + recommendedSeries.map(Series::toPosterUi)).distinctBy { poster -> poster.id },
            )
        }
    }

    override fun onViewChanged(isGrid: Boolean) {
        updateState {
            it.copy(isListSelected = !isGrid)
        }
    }

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(MoviesListEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(MoviesListEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    private fun onFail(errorMsgRes: Int) {
        updateState { it.copy(isLoading = false, errorMsgRes = errorMsgRes) }
    }
}