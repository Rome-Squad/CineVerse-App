package com.giraffe.home.screen.home

import androidx.lifecycle.viewModelScope
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.utils.toHomeUiModel
import com.giraffe.home.utils.toPopularMediaUiModel
import com.giraffe.media.exception.MediaException
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
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
) : BaseViewModel<HomeScreenUiState, HomeEffect>(initialState = HomeScreenUiState()),
    HomeInteractionListener {

    init {
        loadHomeScreen()
        getRecentMovies()
        getRecentSeries()
    }


    private fun loadHomeScreen() {
        updateState { it.copy(isLoading = true, isError = false) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
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
                        isError = false,
                        popularity = popularMovieUi + popularSeriesUi,
                        recentlyReleased = recentMovieUi + recentSeriesUi,
                        topRated = topRatedUi,
                        upcomingMovies = upcomingUi
                    )
                }

            } catch (e: MediaException) {
                updateState { it.copy(isLoading = false, isError = true) }
            }
        }
    }

    private fun onFail(errorMsgRes: Int) {
        updateState { it.copy(isLoading = false, errorMsgRes = errorMsgRes) }
        sendEffect(HomeEffect.ShowError(errorMsgRes))
    }

    private fun getRecentMovies() {
        safeExecute(
            onSuccess = ::onGetRecentMoviesSuccess,
            onError = ::onFail,
            block = getRecentlyMoviesUseCase::invoke
        )
    }

    private suspend fun onGetRecentMoviesSuccess(moviesFlow: Flow<List<Movie>>) {
        moviesFlow.collectLatest { movies ->
            updateState { it.copy(recentlyViewed = (it.recentlyViewed + movies.map(Movie::toHomeUiModel)).distinct()) }
            getRecommendedMovies(movies)
        }
    }

    private fun getRecentSeries() {
        safeExecute(
            onSuccess = ::onGetRecentSeriesSuccess,
            onError = ::onFail,
            block = getRecentlySeriesUseCase::invoke
        )
    }

    private suspend fun onGetRecentSeriesSuccess(seriesFlow: Flow<List<Series>>) {
        seriesFlow.collectLatest { series ->
            updateState { it.copy(recentlyViewed = (it.recentlyViewed + series.map(Series::toHomeUiModel)).distinct()) }
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
        updateState { it.copy(matchVibes = (it.matchVibes + recommendedMovies.map(Movie::toHomeUiModel)).distinct()) }
    }

    private fun getRecommendedSeries(series: List<Series>) {
        series.firstOrNull()?.let { series ->
            safeExecute(
                onSuccess = ::onGetRecommendedSeriesSuccess,
                onError = ::onFail,
            ) {
                getRecommendedSeriesUseCase(series.id.toLong(), 1)
            }
        }
    }

    private fun onGetRecommendedSeriesSuccess(recommendedSeries: List<Series>) {
        updateState { it.copy(matchVibes = (it.matchVibes + recommendedSeries.map(Series::toHomeUiModel)).distinct()) }
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
}