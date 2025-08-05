package com.giraffe.home.screen.home

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.giraffe.home.R
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.utils.toHomeUiModel
import com.giraffe.home.utils.toPopularMediaUiModel
import com.giraffe.home.utils.toUiModel
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.GetPopularityMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetPopularitySeriesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
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
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
) : BaseViewModel<HomeScreenUiState, HomeEffect>(initialState = HomeScreenUiState()),
    HomeInteractionListener {

    init {
        getFeaturedCollection()
        loadHomeContent()
        getRecentViewed()
    }

    private fun getRecentViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetRecentlyMoviesSuccess,
                onError = ::onFail,
                block = getRecentlyMoviesUseCase::invoke
            ).join()
            safeExecute(
                onSuccess = ::onGetRecentlySeresSuccess,
                onError = ::onFail,
                block = getRecentlySeriesUseCase::invoke
            )
        }
    }

    private fun getFeaturedCollection() {
        safeExecute {
            val featuredCollection = getMoviesGenresUseCase().map { it.toUiModel() }
            updateState {
                it.copy(
                    featuredCollections = featuredCollection
                )
            }
        }
    }

    override fun loadHomeContent() {
        updateState { it.copy(isLoading = true, isError = false) }
        safeLaunch(
            onError = ::onLoadHomeContentError,
            onSuccess = ::onLoadHomeContentSuccess
        ) {
            val popularMoviesDeferred = async { getPopularityMoviesUseCase(page = 1) }
            val popularSeriesDeferred = async { getPopularitySeriesUseCase(page = 1, limit = 10) }
            val recentMoviesDeferred = async { getRecentlyReleasedMoviesUseCase(page = 1) }
            val recentSeriesDeferred =
                async { getRecentlyReleasedSeriesUseCase(page = 1, limit = 10) }

            val popularMovies = popularMoviesDeferred.await()
            val popularSeries = popularSeriesDeferred.await()
            val recentMovies = recentMoviesDeferred.await()
            val recentSeries = recentSeriesDeferred.await()
            val topRated = getTopRatedSeriesUseCase(page = 1, limit = 10)
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
                    popularity = popularMovieUi + popularSeriesUi,
                    recentlyReleased = recentMovieUi + recentSeriesUi,
                    topRated = topRatedUi,
                    upcomingMovies = upcomingUi
                )
            }
        }
    }

    private fun onLoadHomeContentError(errorResId: Int) {
        updateState { it.copy(isLoading = false, isError = true) }
        sendEffect(HomeEffect.ShowError(errorResId))
    }

    private fun onLoadHomeContentSuccess(result: Unit) {
        updateState {
            it.copy(
                isLoading = false,
                isError = false
            )
        }
    }

    private suspend fun onGetRecentlyMoviesSuccess(moviesFlow: Flow<List<Movie>>) {
        moviesFlow.collectLatest { movies ->
            updateState { it.copy(recentlyViewed = (it.recentlyViewed + movies.map(Movie::toHomeUiModel)).distinctBy { movie -> movie.id }) }
            getRecommendedMovies(movies)
        }
    }

    private suspend fun onGetRecentlySeresSuccess(seriesFlow: Flow<List<Series>>) {
        seriesFlow.collectLatest { series ->
            updateState { it.copy(recentlyViewed = (it.recentlyViewed + series.map(Series::toHomeUiModel)).distinctBy { series -> series.id }) }
            getRecommendedSeries(series)
        }
    }

    private fun getRecommendedMovies(movies: List<Movie>) {
        movies.firstOrNull()?.let { movie ->
            safeExecute(
                onSuccess = ::onGetRecommendedMoviesSuccess,
                onError = ::onFail,
            ) {
                getRecommendedMovieUseCase(movie.id, 1)
            }
        }
    }

    private fun onGetRecommendedMoviesSuccess(recommendedMovies: List<Movie>) {
        updateState { it.copy(matchVibes = (it.matchVibes + recommendedMovies.map(Movie::toHomeUiModel)).distinctBy { movie -> movie.id }) }
    }

    private fun getRecommendedSeries(series: List<Series>) {
        series.firstOrNull()?.let { series ->
            safeExecute(
                onSuccess = ::onGetRecommendedSeriesSuccess,
                onError = ::onFail,
            ) {
                getRecommendedSeriesUseCase(series.id, 1)
            }
        }
    }

    private fun onGetRecommendedSeriesSuccess(recommendedSeries: List<Series>) {
        updateState { it.copy(matchVibes = (it.matchVibes + recommendedSeries.map(Series::toHomeUiModel)).distinctBy { series -> series.id }) }
    }

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(HomeEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(HomeEffect.NavigateToSeriesDetails(mediaId))
        }
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

    private fun onFail(errorMesRes: Int) = updateState { it.copy(errorMsgRes = errorMesRes) }

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

    override fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String) {
        sendEffect(
            HomeEffect.NavigateToYourCollection(
                collectionId = collectionId,
                collectionTitle = collectionTitle
            )
        )
    }
}
