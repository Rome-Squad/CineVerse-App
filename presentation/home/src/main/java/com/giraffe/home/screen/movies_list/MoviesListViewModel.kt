package com.giraffe.home.screen.movies_list

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.giraffe.home.R
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.screen.home.MediaType
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.MediaException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeriesUseCase: GetRecentlyReleasedSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
    stateSavedStateHandle: SavedStateHandle
) : BaseViewModel<MoviesListUiState, MoviesListEffect>(initialState = MoviesListUiState()),
    MoviesListInteractionListener {
    private val sectionTitle = stateSavedStateHandle.toRoute<MoviesListRoute>().sectionTitle
    private val sectionType = stateSavedStateHandle.toRoute<MoviesListRoute>().sectionType

    init {
        loadMoviesBySection(sectionType)
    }

    private fun loadMoviesBySection(sectionType: String) {
        updateState {
            it.copy(
                isLoading = true,
                errorMessage = null,
                mediaList = emptyList(),
                moviesListTitle = sectionTitle
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val media = when (sectionType) {
                    MovieSectionType.RECENTLY_RELEASED -> {
                        val recentMovies = getRecentlyReleasedMoviesUseCase(page = 1)
                        val recentSeries = getRecentlyReleasedSeriesUseCase(page = 1)

                        val moviesUi = recentMovies.map { it.toPosterUi() }
                        val seriesUi = recentSeries.map { it.toPosterUi() }

                        moviesUi + seriesUi
                    }

                    MovieSectionType.TOP_RATED_TV_SHOWS -> {
                        val topRated = getTopRatedSeriesUseCase(page = 1)
                        topRated.map { it.toPosterUi() }
                    }

                    MovieSectionType.UPCOMING_MOVIES -> {
                        val upcoming = getUpcomingMoviesUseCase(page = 1)
                        upcoming.map { it.toPosterUi() }
                    }

                    MovieSectionType.RECENTLY_VIEWED -> {
                        val recentlyViewedMovies = getRecentlyMoviesUseCase()
                        val recentlyViewedSeries = getRecentlySeriesUseCase()

                        val moviesUi = recentlyViewedMovies.map { it.toPosterUi() }
                        val seriesUi = recentlyViewedSeries.map { it.toPosterUi() }

                        moviesUi + seriesUi
                    }

                    MovieSectionType.MATCHES_YOUR_VIBES -> {
                        val recentlyViewedMovies = getRecentlyMoviesUseCase()
                        val recentlyViewedSeries = getRecentlySeriesUseCase()

                        val recommendedMovies = recentlyViewedMovies.firstOrNull()
                            ?.let { getRecommendedMovieUseCase(it.id, 1) } ?: emptyList()
                        val recommendedSeries = recentlyViewedSeries.firstOrNull()
                            ?.let { getRecommendedSeriesUseCase(it.id.toLong(), 1) } ?: emptyList()

                        val moviesUi = recommendedMovies.map { it.toPosterUi() }
                        val seriesUi = recommendedSeries.map { it.toPosterUi() }

                        moviesUi + seriesUi
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

            } catch (e: MediaException) {
                val errorResId = mapExceptionToStringRes(e)
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
                sendEffect(MoviesListEffect.ShowError(errorResId))
            } catch (e: Exception) {
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
                sendEffect(MoviesListEffect.ShowError(R.string.error_unknown))
            }
        }
    }


    @StringRes
    private fun mapExceptionToStringRes(throwable: Throwable): Int {
        return when (throwable) {
            is AccessDeniedException -> R.string.error_access_denied
            is ValidationException -> R.string.error_validation
            is NotFoundException -> R.string.error_not_found
            is UnknownException -> R.string.error_unknown
            else -> R.string.error_unknown
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
}