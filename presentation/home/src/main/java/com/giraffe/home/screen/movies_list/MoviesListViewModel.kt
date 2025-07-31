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
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMoviesByGenresUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeriesUseCase: GetRecentlyReleasedSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getMoviesByGenresUseCase: GetMoviesByGenresUseCase,
    stateSavedStateHandle: SavedStateHandle
) : BaseViewModel<MoviesListUiState, MoviesListEffect>(initialState = MoviesListUiState()),
    MoviesListInteractionListener {
    private val sectionTitle = stateSavedStateHandle.toRoute<MoviesListRoute>().sectionTitle
    private val sectionType = stateSavedStateHandle.toRoute<MoviesListRoute>().sectionType
    private val collectionId = stateSavedStateHandle.toRoute<MoviesListRoute>().collectionId

    init {
        sectionType?.let { loadMoviesBySection(it) }
        collectionId?.let { loadMoviesByGenres(it) }
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
                val errorResId = mapExceptionToStringRes(e)
                updateState { it.copy(isLoading = false, errorMessage = e.message) }
                sendEffect(MoviesListEffect.ShowError(errorResId))
            }
        }
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
                        recentMovies.map { it.toPosterUi() } + recentSeries.map { it.toPosterUi() }
                    }

                    MovieSectionType.TOP_RATED_TV_SHOWS -> {
                        val topRated = getTopRatedSeriesUseCase(page = 1)
                        topRated.map { it.toPosterUi() }
                    }

                    MovieSectionType.UPCOMING_MOVIES -> {
                        val upcoming = getUpcomingMoviesUseCase(page = 1)
                        upcoming.map { it.toPosterUi() }
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
        val recentMovies = getRecentlyMoviesUseCase().first()
        updateState {
            it.copy(
                isLoading = false,
                errorMessage = null,
                mediaList = recentMovies.map(Movie::toPosterUi),
            )
        }
    }
}

private fun getRecommended() {
    safeExecute {
        val recentMovies = getRecentlyMoviesUseCase().first()
        val recommendedMovies = recentMovies.firstOrNull()?.id?.let { movieId ->
            getRecommendedMovieUseCase(movieId = movieId, page = 1).map(Movie::toPosterUi)
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
}