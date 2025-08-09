package com.giraffe.presentation.home.screen.home

import androidx.lifecycle.viewModelScope
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movies.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movies.usecase.GetPopularityMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.movies.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetPopularitySeriesUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.base.BaseViewModel
import com.giraffe.presentation.home.screen.show_more.ShowMoreSectionType
import com.giraffe.presentation.home.utils.toHomeUiModel
import com.giraffe.presentation.home.utils.toPopularMediaUiModel
import com.giraffe.presentation.home.utils.toUiModel
import com.giraffe.user.usecase.GetUserNameUseCase
import com.giraffe.user.usecase.IsLoggedInUseCase
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
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase,
    private val getRecentlyViewedMoviesUseCase: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentSeriesUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase
) : BaseViewModel<HomeScreenState, HomeEffect>(initialState = HomeScreenState()),
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
        isLoggedIn()
        getYourCollections()
    }


    private fun isLoggedIn() {
        safeExecute(
            onSuccess = ::onIsLoggedInSuccess,
            onError = ::onFail,
            block = { isLoggedInUseCase() }
        )
    }

    private fun onIsLoggedInSuccess(isLoggedIn: Boolean) {
        updateState { it.copy(isLoggedIn = isLoggedIn) }
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

    private fun getYourCollections() {
        safeExecute(
            onSuccess = ::onGetYourCollectionsSuccess,
            onError = ::onFail,
            block = { getCollectionsUseCase() }
        )
    }

    private fun onGetYourCollectionsSuccess(collections: List<Collection>) {
        val yourCollections = collections.map { it.toUiModel() }
        updateState { currentState ->
            currentState.copy(
                yourCollections = yourCollections
            )
        }
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
            )
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
            )
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
                block = getRecentlyViewedMoviesUseCase::invoke
            )
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


    override fun onWhatShouldIWatchClicked(sectionType: ShowMoreSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }

    override fun onFeaturedCollectionClicked(collectionId: Int, collectionTitle: String) {
        sendEffect(
            HomeEffect.NavigateToFeaturedCollection(
                collectionId = collectionId,
                collectionTitle = collectionTitle
            )
        )
    }

    override fun onYourCollectionClicked() {
        sendEffect(
            HomeEffect.NavigateToYourCollection
        )
    }

    override fun onExploreSectionClicked() {
        sendEffect(HomeEffect.NavigateToExploreScreen)
    }

    override fun onMatchSectionClicked() {
        sendEffect(HomeEffect.NavigateToMatchScreen)
    }

    override fun onCollectionClick(collectionId: Int, collectionName: String) {
        sendEffect(
            HomeEffect.NavigateToCollection(
                collectionId = collectionId,
                collectionName = collectionName
            )
        )
    }
    override fun onSeeAllRecentlyReleasedClicked(sectionType: ShowMoreSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllTopRatedClicked(sectionType: ShowMoreSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllUpcomingClicked(sectionType: ShowMoreSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllRecentlyViewedClicked(sectionType: ShowMoreSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }
}