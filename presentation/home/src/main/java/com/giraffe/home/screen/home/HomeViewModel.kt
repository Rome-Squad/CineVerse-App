package com.giraffe.home.screen.home

import androidx.annotation.StringRes
import com.giraffe.home.R
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.utils.toHomeUiModel
import com.giraffe.home.utils.toPopularMediaUiModel
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetPopularityMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.usecase.GetPopularitySeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularityMoviesUseCase: GetPopularityMoviesUseCase,
    private val getPopularitySeriesUseCase: GetPopularitySeriesUseCase,
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeriesUseCase: GetRecentlyReleasedSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesGenresByIdsUseCase: GetMovieGenresUseCase,
    private val getRecentlyMoviesUseCase: GetRecentlyMoviesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
) : BaseViewModel<HomeScreenUiState, HomeEffect>(initialState = HomeScreenUiState()),
    HomeInteractionListener {

    init {
        loadHomeScreen()
        getRecentMovies()
    }


 private fun loadHomeScreen() {
    updateState { it.copy(isLoading = true, isGenricError = false) }

    safeExecute {
        val popularMoviesDeferred = async { getPopularityMoviesUseCase(page = 1) }
        val popularSeriesDeferred = async { getPopularitySeriesUseCase(page = 1) }
        val recentMoviesDeferred = async { getRecentlyReleasedMoviesUseCase(page = 1) }
        val recentSeriesDeferred = async { getRecentlyReleasedSeriesUseCase(page = 1) }

        val popularMovies = popularMoviesDeferred.await()
        val popularSeries = popularSeriesDeferred.await()
        val recentMovies = recentMoviesDeferred.await()
        val recentSeries = recentSeriesDeferred.await()
        val topRated = getTopRatedSeriesUseCase(page = 1)
        val upcoming = getUpcomingMoviesUseCase(page = 1)

        val popularMovieUi = popularMovies.map { movie ->
            val genres = getMoviesGenresByIdsUseCase(movie.genresID).map { it.title }
            movie.toPopularMediaUiModel(genres)
        }

        val popularSeriesUi = popularSeries.map { series ->
            val genres = getSeriesGenresByIdsUseCase(series.genreIDs).map { it.title }
            series.toPopularMediaUiModel(genres)
        }

        val recentMovieUi = recentMovies.map { it.toHomeUiModel() }
        val recentSeriesUi = recentSeries.map { it.toHomeUiModel() }
        val topRatedUi = topRated.map { it.toHomeUiModel() }
        val upcomingUi = upcoming.map { it.toHomeUiModel() }

        updateState {
            it.copy(
                isLoading = false,
                isGenricError = false,
                popularity = popularMovieUi + popularSeriesUi,
                recentlyReleased = recentMovieUi + recentSeriesUi,
                topRated = topRatedUi,
                upcomingMovies = upcomingUi
            )
        }
    }
}

 private fun getRecentMovies() {
    safeExecute {
        getRecentlyMoviesUseCase().collectLatest { movies ->
            updateState { it.copy(recentlyViewed = movies.map(Movie::toHomeUiModel)) }

            movies.firstOrNull()?.let { movie ->
                val recommendedMovies = getRecommendedMovieUseCase(movie.id, 1)
                    .map(Movie::toHomeUiModel)

                updateState { it.copy(matchVibes = recommendedMovies) }
            }
        }
    }
}

    @StringRes
    private fun mapExceptionToStringRes(throwable: Throwable): Int {
        return when (throwable) {
            is NoInternetException -> R.string.error_network
            is AccessDeniedException -> R.string.error_access_denied
            is ValidationException -> R.string.error_validation
            is NotFoundException -> R.string.error_not_found
            is UnknownException -> R.string.error_unknown
            else -> R.string.error_unknown
        }
    }
    override fun onError(throwable: Throwable) {
        val resId = mapExceptionToStringRes(throwable)
        val isNetworkError = throwable is NoInternetException

        updateState {
            it.copy(
                isLoading = false,
                isGenricError = true,
                isNetworkError = isNetworkError
            )
        }
        sendEffect(HomeEffect.ShowError(resId))
    }
    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(HomeEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(HomeEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun loadHomeContent() {
        loadHomeScreen()
    }

    override fun onSeeAllRecentlyReleasedClicked(sectionTitle: String, sectionType: String) {
        sendEffect(
            HomeEffect.NavigateToRecentlyReleasedList(
                sectionTitle = sectionTitle,
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllTopRatedClicked(sectionTitle: String, sectionType: String) {
        sendEffect(
            HomeEffect.NavigateToTopRatedList(
                sectionTitle = sectionTitle,
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllUpcomingClicked(sectionTitle: String, sectionType: String) {
        sendEffect(
            HomeEffect.NavigateToUpcomingList(
                sectionTitle = sectionTitle,
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllRecentlyViewedClicked(sectionTitle: String, sectionType: String) {
        sendEffect(
            HomeEffect.NavigateToRecentlyViewedList(
                sectionTitle = sectionTitle,
                sectionType = sectionType
            )
        )
    }

    override fun onWhatShouldIWatchClicked(sectionTitle: String, sectionType: String) {
        sendEffect(
            HomeEffect.NavigateToRecommendedList(
                sectionTitle = sectionTitle,
                sectionType = sectionType
            )
        )
    }
}