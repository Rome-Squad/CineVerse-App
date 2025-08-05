package com.giraffe.home.screen.home

import androidx.lifecycle.viewModelScope
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.utils.toHomeUiModel
import com.giraffe.home.utils.toPopularMediaUiModel
import com.giraffe.home.utils.toUiModel
import com.giraffe.media.entity.Genre
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
import com.giraffe.user.usecase.GetUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val getUserNameUseCase: GetUserNameUseCase,
) : BaseViewModel<HomeScreenUiState, HomeEffect>(initialState = HomeScreenUiState()),
    HomeInteractionListener {

    init {
        loadHomeContent()
    }

    override fun loadHomeContent() {
        getFeaturedCollection()
        getRecentViewed()
        getPopularity()
        getRecentlyReleased()
        getTopRatedSeries()
        getUpcomingMovies()
        getUserName()
    }

    private fun getUserName() {
        safeExecute(
            onSuccess = ::getUseNameSuccess,
            onError = ::onFail,
            block = { getUserNameUseCase() }
        )
    }

    private fun getUseNameSuccess(userName: String) {
        updateState { it.copy(userName = userName) }
    }

    private fun getFeaturedCollection() {
        safeExecute(
            onSuccess = ::onGetFeaturedCollectionSuccess,
            onError = ::onFail,
            block = { getMoviesGenresUseCase() })
    }

    private fun onGetFeaturedCollectionSuccess(genres: List<Genre>) {
        val featuredCollection = genres.map { it.toUiModel() }
        updateState { it.copy(featuredCollections = featuredCollection) }
    }

    private fun getPopularity() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetPopularityMoviesSuccess,
                onError = ::onFail,
                block = { getPopularityMoviesUseCase(page = 1) }
            ).join()
            safeExecute(
                onSuccess = ::onGetPopularitySeriesSuccess,
                onError = ::onFail,
                block = { getPopularitySeriesUseCase(page = 1, limit = 10) }
            )
        }
    }

    private suspend fun onGetPopularityMoviesSuccess(movies: List<Movie>) {
        val popularMoviesUi = movies.map { movie ->
            val genres = getMoviesGenresByIdsUseCase(movie.genresID).map { it.title }
            movie.toPopularMediaUiModel(genres)
        }
        updateState { currentState ->
            currentState.copy(
                popularity =
                    currentState.popularity + popularMoviesUi
            )
        }
    }

    private suspend fun onGetPopularitySeriesSuccess(series: List<Series>) {
        val popularSeriesUi = series.map { series ->
            val genres = getSeriesGenresByIdsUseCase(series.genreIDs).map { it.title }
            series.toPopularMediaUiModel(genres)
        }
        updateState { currentState ->
            currentState.copy(
                popularity =
                    currentState.popularity + popularSeriesUi
            )
        }
    }

    private fun getRecentlyReleased() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetRecentlyReleasedMoviesSuccess,
                onError = ::onFail,
                block = { getRecentlyReleasedMoviesUseCase(page = 1) }
            ).join()
            safeExecute(
                onSuccess = ::onGetRecentlyReleasedSeriesSuccess,
                onError = ::onFail,
                block = { getRecentlyReleasedSeriesUseCase(page = 1, limit = 10) }
            )
        }
    }

    private fun onGetRecentlyReleasedMoviesSuccess(movies: List<Movie>) {
        val recentMoviesUi = movies.map { it.toHomeUiModel() }
        updateState { currentState ->
            currentState.copy(
                recentlyReleased =
                    currentState.recentlyReleased + recentMoviesUi
            )
        }
    }

    private fun onGetRecentlyReleasedSeriesSuccess(series: List<Series>) {
        val recentSeriesUi = series.map { it.toHomeUiModel() }
        updateState { currentState ->
            currentState.copy(
                recentlyReleased =
                    currentState.recentlyReleased + recentSeriesUi
            )
        }
    }

    private fun getTopRatedSeries() {
        safeExecute(
            onSuccess = ::onGetTopRatedSeriesSuccess,
            onError = ::onFail,
            block = { getTopRatedSeriesUseCase(page = 1, limit = 10) }
        )
    }

    private fun onGetTopRatedSeriesSuccess(series: List<Series>) {
        val topRatedUi = series.map { it.toHomeUiModel() }
        updateState { currentState ->
            currentState.copy(
                topRated = currentState.topRated + topRatedUi
            )
        }
    }

    private fun getUpcomingMovies() {
        safeExecute(
            onSuccess = ::onGetUpcomingMoviesSuccess,
            onError = ::onFail,
            block = { getUpcomingMoviesUseCase(page = 1) }
        )
    }

    private fun onGetUpcomingMoviesSuccess(movies: List<Movie>) {
        val upcomingUi = movies.map { it.toHomeUiModel() }
        updateState { currentState ->
            currentState.copy(
                upcomingMovies = currentState.upcomingMovies + upcomingUi
            )
        }
    }

    private fun getRecentViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetRecentlyMoviesSuccess,
                onError = ::onFail,
                block = getRecentlyMoviesUseCase::invoke
            ).join()
            safeExecute(
                onSuccess = ::onGetRecentlySeriesSuccess,
                onError = ::onFail,
                block = getRecentlySeriesUseCase::invoke
            )
        }
    }

    private suspend fun onGetRecentlyMoviesSuccess(moviesFlow: Flow<List<Movie>>) {
        moviesFlow.collectLatest { movies ->
            updateState { it.copy(recentlyViewed = (it.recentlyViewed + movies.map(Movie::toHomeUiModel)).distinctBy { movie -> movie.id }) }
            getRecommendedMovies(movies)
        }
    }

    private suspend fun onGetRecentlySeriesSuccess(seriesFlow: Flow<List<Series>>) {
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

    private fun onFail(errorMesRes: Int) = updateState {
        it.copy(
            isLoading = false,
            isError = true,
            errorMsgRes = errorMesRes
        )
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

    override fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String) {
        sendEffect(
            HomeEffect.NavigateToYourCollection(
                collectionId = collectionId,
                collectionTitle = collectionTitle
            )
        )
    }
}
