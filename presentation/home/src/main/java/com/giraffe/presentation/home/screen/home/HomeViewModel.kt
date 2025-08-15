package com.giraffe.presentation.home.screen.home

import android.accounts.NetworkErrorException
import androidx.lifecycle.viewModelScope
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresUseCase
import com.giraffe.media.movie.usecase.GetPopularMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.GetRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetMatchesYourVibeSeriesUseCase
import com.giraffe.media.series.usecase.GetPopularitySeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import com.giraffe.presentation.home.base.BaseViewModel
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.navigation.home.routes.MixedMediaSectionType
import com.giraffe.presentation.home.utils.toPopularMediaUi
import com.giraffe.presentation.home.utils.toPoster
import com.giraffe.presentation.home.utils.toUi
import com.giraffe.user.usecase.GetUserNameUseCase
import com.giraffe.user.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getPopularitySeriesUseCase: GetPopularitySeriesUseCase,
    private val getRecentlyReleasedMoviesUseCase: GetRecentlyReleasedMoviesUseCase,
    private val getRecentlyReleasedSeriesUseCase: GetRecentlyReleasedSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase,
    private val getRecentlyViewedMoviesUseCase: GetRecentlyViewedMoviesUseCase,
    private val getRecentlySeriesUseCase: GetRecentlyViewedSeriesUseCase,
    private val getMatchesYourVibeMoviesUseCase: GetMatchesYourVibeMoviesUseCase,
    private val getMatchesYourVibeSeriesUseCase: GetMatchesYourVibeSeriesUseCase,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase
) : BaseViewModel<HomeScreenState, HomeEffect>(initialState = HomeScreenState()),
    HomeInteractionListener {

    init {
        getRecentViewed()
        getPopularity()
        getRecentlyReleased()
        getTopRatedSeries()
        getUpcomingMovies()
        getMatchesYourVibeMovies()
        getMatchesYourVibeSeries()
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
        updateState { it.copy(userName = userName, isNoInternet = false, isLoading = false) }
    }

    private fun getYourCollections() {
        safeExecute(
            onSuccess = ::onGetYourCollectionsSuccess,
            onError = ::onFail,
            block = { getCollectionsUseCase() }
        )
    }

    private fun onGetYourCollectionsSuccess(collections: List<Collection>) {
        updateState { currentState ->
            currentState.copy(
                yourCollections = collections.map(Collection::toUi),
                isNoInternet = false,
                isLoading = false
            )
        }
    }


    private fun getPopularity() {
        viewModelScope.launch(Dispatchers.IO) {
            safeExecute(
                onSuccess = ::onGetPopularityMoviesSuccess,
                onError = ::onFail,
                block = { getPopularMoviesUseCase(page = 1) }
            )
            safeExecute(
                onSuccess = ::onGetPopularitySeriesSuccess,
                onError = ::onFail,
                block = { getPopularitySeriesUseCase(page = 1, limit = 10) }
            )
        }
    }

    private fun onGetPopularityMoviesSuccess(movies: List<Movie>) {
        safeExecute {
            val popularMoviesUi = movies.map { movie ->
                val genres = getMoviesGenresByIdsUseCase(movie.genresID).map { it.title }
                movie.toPopularMediaUi(genres)
            }
            updateState { currentState ->
                currentState.copy(
                    popularity =
                        currentState.popularity + popularMoviesUi,
                    isNoInternet = false,
                    isLoading = false
                )
            }
        }
    }

    private fun onGetPopularitySeriesSuccess(series: List<Series>) {
        safeExecute {
            val popularSeriesUi = series.map { seriesList ->
                val genres = getSeriesGenresByIdsUseCase(seriesList.genreIDs).map { it.title }
                seriesList.toPopularMediaUi(genres)
            }
            updateState { currentState ->
                currentState.copy(
                    popularity =
                        currentState.popularity + popularSeriesUi,
                    isNoInternet = false,
                    isLoading = false
                )
            }
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
        updateState { currentState ->
            currentState.copy(
                recentlyReleased =
                    currentState.recentlyReleased + movies.map(Movie::toPoster),
                isNoInternet = false,
                isLoading = false
            )
        }
    }

    private fun onGetRecentlyReleasedSeriesSuccess(series: List<Series>) {
        updateState { currentState ->
            currentState.copy(
                recentlyReleased =
                    currentState.recentlyReleased + series.map(Series::toPoster),
                isNoInternet = false,
                isLoading = false
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
        updateState { currentState ->
            currentState.copy(
                topRated = currentState.topRated + series.map(Series::toPoster),
                isNoInternet = false,
                isLoading = false
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
        updateState { currentState ->
            currentState.copy(
                upcomingMovies = currentState.upcomingMovies + movies.map(Movie::toPoster),
                isNoInternet = false,
                isLoading = false
            )
        }
    }

    private fun getRecentViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCollect(
                onEmitNewValue = ::onGetRecentlyMoviesSuccess,
                onError = ::onFail,
                block = getRecentlyViewedMoviesUseCase::invoke
            )
            safeCollect(
                onEmitNewValue = ::onGetRecentlySeriesSuccess,
                onError = ::onFail,
                block = { getRecentlySeriesUseCase.invoke() }
            )
        }
    }

    private fun onGetRecentlyMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                recentlyViewed = (
                        it.recentlyViewed + movies.map(Movie::toPoster)).distinctBy { movie -> movie.id },
                isNoInternet = false,
                isLoading = false
            )
        }
    }

    private fun onGetRecentlySeriesSuccess(series: List<Series>) {
        updateState {
            it.copy(
                recentlyViewed = (it.recentlyViewed + series.map(Series::toPoster)).distinctBy { series -> series.id },
                isNoInternet = false,
                isLoading = false
            )
        }
    }

    private fun getMatchesYourVibeMovies() {
        safeExecute(
            onSuccess = ::onGetMatchesYourVibeMoviesSuccess,
            onError = ::onFail,
        ) {
            getMatchesYourVibeMoviesUseCase(page = 1, limit = 10)
        }
    }

    private fun onGetMatchesYourVibeMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(
                matchVibes = (it.matchVibes + movies.map(Movie::toPoster)).distinctBy { movie -> movie.id },
                isNoInternet = false,
                isLoading = false
            )
        }
    }

    private fun getMatchesYourVibeSeries() {
        safeExecute(
            onSuccess = ::onGetMatchesYourVibeSeriesSuccess,
            onError = ::onFail,
        ) {
            getMatchesYourVibeSeriesUseCase(1, 10)
        }
    }

    private fun onGetMatchesYourVibeSeriesSuccess(matchesYourVibeSeries: List<Series>) {
        updateState {
            it.copy(
                matchVibes = (it.matchVibes + matchesYourVibeSeries.map(Series::toPoster)).distinctBy { series -> series.id },
                isNoInternet = false,
                isLoading = false
            )
        }
    }

    private fun onFail(error: Throwable) = updateState {
        it.copy(
            isLoading = false,
            isNoInternet = error is NetworkErrorException
        )
    }

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(HomeEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(HomeEffect.NavigateToSeriesDetails(mediaId))
        }
    }


    override fun onMatchYourVibeClicked(sectionType: MixedMediaSectionType) {
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

    override fun onLateNightThrillsFeatureClicked(sectionType: MixedMediaSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllRecentlyReleasedClicked(sectionType: MixedMediaSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllTopRatedClicked(sectionType: MixedMediaSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllUpcomingClicked(sectionType: MixedMediaSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }

    override fun onSeeAllRecentlyViewedClicked(sectionType: MixedMediaSectionType) {
        sendEffect(
            HomeEffect.NavigateToShowMore(
                sectionType = sectionType
            )
        )
    }
}