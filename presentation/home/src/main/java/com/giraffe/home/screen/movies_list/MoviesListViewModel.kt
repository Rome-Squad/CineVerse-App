package com.giraffe.home.screen.movies_list

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.home.R
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.screen.home.MediaType
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeriesUseCase: GetRecentlyReleasedSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
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

        when (sectionType) {
            MovieSectionType.RECENTLY_VIEWED -> getRecent()
            MovieSectionType.MATCHES_YOUR_VIBES -> getRecommended()

            else -> {
                safeExecute {
                    val media = when (sectionType) {
                        MovieSectionType.RECENTLY_RELEASED -> {
                            val recentMovies = getRecentlyReleasedMoviesUseCase(page = 1)
                            val recentSeries = getRecentlyReleasedSeriesUseCase(page = 1)
                            recentMovies.map { movie ->
                                movie.toPosterUi(getMovieGenresUseCase(movie.genresID).map { it.title })
                            } + recentSeries.map { series ->
                                series.toPosterUi(getMovieGenresUseCase(series.genreIDs).map { it.title })
                            }
                        }

                        MovieSectionType.TOP_RATED_TV_SHOWS -> {
                            getTopRatedSeriesUseCase(page = 1).map { series ->
                                series.toPosterUi(getMovieGenresUseCase(series.genreIDs).map { it.title })
                            }
                        }

                        MovieSectionType.UPCOMING_MOVIES -> {
                            getUpcomingMoviesUseCase(page = 1).map { movie ->
                                movie.toPosterUi(getMovieGenresUseCase(movie.genresID).map { it.title })
                            }
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
        }
    }

    private fun getRecent() {
        safeExecute {
            val recentMovies = getRecentlyMoviesUseCase().first().map { movie ->
                movie.toPosterUi(getMovieGenresUseCase(movie.genresID).map { it.title })
            }
            updateState {
                it.copy(
                    isLoading = false,
                    errorMessage = null,
                    mediaList = recentMovies,
                )
            }
        }
    }

    private fun getRecommended() {
        safeExecute {
            val recentMovies = getRecentlyMoviesUseCase().first()
            val recommendedMovies = recentMovies.firstOrNull()?.id?.let { movieId ->
                getRecommendedMovieUseCase(movieId = movieId, page = 1).map { movie ->
                    movie.toPosterUi(getMovieGenresUseCase(movie.genresID).map { it.title })
                }
            } ?: emptyList()

            updateState {
                it.copy(
                    isLoading = false,
                    errorMessage = null,
                    mediaList = recommendedMovies,
                )
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

    override fun onError(throwable: Throwable) {
        val errorResId = mapExceptionToStringRes(throwable)
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = throwable.message ?: "Unknown error"
            )
        }
        sendEffect(MoviesListEffect.ShowError(errorResId))
    }
}