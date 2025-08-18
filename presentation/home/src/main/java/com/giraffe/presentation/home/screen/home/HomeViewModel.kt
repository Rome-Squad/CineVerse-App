package com.giraffe.presentation.home.screen.home

import androidx.lifecycle.viewModelScope
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.ObservePopularMoviesUseCase
import com.giraffe.media.movie.usecase.genre.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.matchesYourVibe.ObserveMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyReleased.ObserveRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.upcoming.ObserveUpcomingMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.ObservePopularSeriesUseCase
import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.matchesYourVibe.ObserveMatchesYourVibeSeriesUseCase
import com.giraffe.media.series.usecase.recentlyReleased.ObserveRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.topRated.ObserveTopRatedSeriesUseCase
import com.giraffe.presentation.home.base.BaseViewModel
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.utils.toPopularMediaUi
import com.giraffe.presentation.home.utils.toPoster
import com.giraffe.presentation.home.utils.toUi
import com.giraffe.user.usecase.GetUserNameUseCase
import com.giraffe.user.usecase.IsLoggedInByAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observePopularMoviesUseCase: ObservePopularMoviesUseCase,
    private val observePopularSeriesUseCase: ObservePopularSeriesUseCase,
    private val observeRecentlyReleasedMoviesUseCase: ObserveRecentlyReleasedMoviesUseCase,
    private val observeRecentlyReleasedSeriesUseCase: ObserveRecentlyReleasedSeriesUseCase,
    private val observeTopRatedSeriesUseCase: ObserveTopRatedSeriesUseCase,
    private val observeUpcomingMoviesUseCase: ObserveUpcomingMoviesUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase,
    private val observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
    private val getRecentlyViewedSeriesUseCase: GetRecentlyViewedSeriesUseCase,
    private val observeMatchesYourVibeMoviesUseCase: ObserveMatchesYourVibeMoviesUseCase,
    private val observeMatchesYourVibeSeriesUseCase: ObserveMatchesYourVibeSeriesUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val isLoggedInByAccountUseCase: IsLoggedInByAccountUseCase
) : BaseViewModel<HomeScreenState, HomeEffect>(initialState = HomeScreenState()),
    HomeInteractionListener {

    init {
        loadHomeScreenData()
    }


    private fun loadHomeScreenData() {
        updateState {
            it.copy(
                isNoInternet = false,
                isLoadingUserName = true,
                isLoadingPopularity = true,
                isLoadingRecentlyReleased = true,
                isLoadingUpcomingMovies = true,
                isLoadingTopRatedSeries = true
            )
        }
        isLoggedIn()
        getPopularity()
        getRecentlyReleased()
        getUpcomingMovies()
        getMatchesYourVibe()
        getTopRatedSeries()
        getRecentViewed()
        getUserName()
        getYourCollections()
    }

    private fun isLoggedIn() {
        safeExecute(
            onSuccess = ::onIsLoggedInSuccess,
            onError = ::onError,
            block = { isLoggedInByAccountUseCase() }
        )
    }

    private fun onIsLoggedInSuccess(isLoggedIn: Boolean) {
        updateState { it.copy(isLoggedIn = isLoggedIn) }
    }

    private fun getUserName() {
        safeExecute(
            onSuccess = ::getUseNameSuccess,
            onError = ::onError,
            block = { getUserNameUseCase() }
        )
    }

    private fun getUseNameSuccess(userName: String) {
        updateState { it.copy(userName = userName, isLoadingUserName = false) }
    }

    private fun getYourCollections() {
        safeCollect(
            onEmitNewValue = ::onGetYourCollectionsSuccess,
            onError = ::onError,
            block = getCollectionsUseCase::invoke
        )
    }

    private fun onGetYourCollectionsSuccess(collections: List<Collection>) {
        updateState { currentState ->
            currentState.copy(
                yourCollections = collections.map(Collection::toUi)
            )
        }
    }


    private fun getPopularity() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCollect(
                onEmitNewValue = ::onGetPopularityMoviesSuccess,
                onError = ::onError,
                block = observePopularMoviesUseCase::invoke
            )
            safeCollect(
                onEmitNewValue = ::onGetPopularitySeriesSuccess,
                onError = ::onError,
                block = observePopularSeriesUseCase::invoke
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
                    popularity = (popularMoviesUi + currentState.popularity).distinctBy { it.id },
                    isLoadingPopularity = false
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
                    popularity = (popularSeriesUi + currentState.popularity).distinctBy { it.id },
                    isLoadingPopularity = false
                )
            }
        }
    }

    private fun getRecentlyReleased() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCollect(
                onEmitNewValue = ::onGetRecentlyReleasedMoviesSuccess,
                onError = ::onError,
                block = observeRecentlyReleasedMoviesUseCase::invoke
            )
            safeCollect(
                onEmitNewValue = ::onGetRecentlyReleasedSeriesSuccess,
                onError = ::onError,
                block = observeRecentlyReleasedSeriesUseCase::invoke
            )
        }
    }

    private fun onGetRecentlyReleasedMoviesSuccess(movies: List<Movie>) {
        updateState { currentState ->
            currentState.copy(
                recentlyReleased = (movies.map(Movie::toPoster) + currentState.recentlyReleased).distinctBy { it.id },
                isLoadingRecentlyReleased = false
            )
        }
    }

    private fun onGetRecentlyReleasedSeriesSuccess(series: List<Series>) {
        updateState { currentState ->
            currentState.copy(
                recentlyReleased = (series.map(Series::toPoster) + currentState.recentlyReleased).distinctBy { it.id },
                isLoadingRecentlyReleased = false
            )
        }
    }

    private fun getTopRatedSeries() {
        safeCollect(
            onEmitNewValue = ::onGetTopRatedSeriesSuccess,
            onError = ::onError,
            block = observeTopRatedSeriesUseCase::invoke
        )
    }

    private fun onGetTopRatedSeriesSuccess(series: List<Series>) {
        updateState { currentState ->
            currentState.copy(
                topRated = (series.map(Series::toPoster) + currentState.topRated).distinctBy { it.id },
                isLoadingTopRatedSeries = false
            )
        }
    }

    private fun getUpcomingMovies() {
        safeCollect(
            onEmitNewValue = ::onGetUpcomingMoviesSuccess,
            onError = ::onError,
            block = observeUpcomingMoviesUseCase::invoke
        )
    }

    private fun onGetUpcomingMoviesSuccess(movies: List<Movie>) {
        updateState { currentState ->
            currentState.copy(
                upcomingMovies = (movies.map(Movie::toPoster) + currentState.upcomingMovies).distinctBy { it.id },
                isLoadingUpcomingMovies = false
            )
        }
    }

    private fun getRecentViewed() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCollect(
                onEmitNewValue = ::onGetRecentlyMoviesSuccess,
                onError = ::onError,
                block = observeRecentlyViewedMoviesUseCase::invoke
            )
            safeCollect(
                onEmitNewValue = ::onGetRecentlySeriesSuccess,
                onError = ::onError,
                block = getRecentlyViewedSeriesUseCase::invoke
            )
        }
    }

    private fun onGetRecentlyMoviesSuccess(movies: List<Movie>) {
        updateState {
            it.copy(recentlyViewed = (movies.map(Movie::toPoster) + it.recentlyViewed).distinctBy { movie -> movie.id })
        }
    }

    private fun onGetRecentlySeriesSuccess(series: List<Series>) {
        updateState {
            it.copy(
                recentlyViewed = (it.recentlyViewed + series.map(Series::toPoster)).distinctBy { series -> series.id }
            )
        }
    }


    private fun getMatchesYourVibe() {
        safeCollect(
            onEmitNewValue = ::onGetMatchesYourVibeMoviesSuccess,
            onError = ::onError,
            block = observeMatchesYourVibeMoviesUseCase::invoke
        )
        safeCollect(
            onEmitNewValue = ::onGetMatchesYourVibeSeriesSuccess,
            onError = ::onError,
            block = observeMatchesYourVibeSeriesUseCase::invoke
        )
    }

    private fun onGetMatchesYourVibeMoviesSuccess(movies: List<Movie>) {
        updateState { currentState ->
            currentState.copy(
                matchVibes = (movies.map(Movie::toPoster) + currentState.matchVibes).distinctBy { it.id }
            )
        }
    }

    private fun onGetMatchesYourVibeSeriesSuccess(series: List<Series>) {
        updateState { currentState ->
            currentState.copy(
                matchVibes = (series.map(Series::toPoster) + currentState.matchVibes).distinctBy { it.id }
            )
        }
    }

    private fun onError(error: Throwable) = updateState {
        it.copy(
            isLoadingRecentlyReleased = false,
            isLoadingUpcomingMovies = false,
            isLoadingPopularity = false,
            isLoadingTopRatedSeries = false,
            isLoadingUserName = false,
            isNoInternet = error is NoInternetException
        )
    }

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(HomeEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(HomeEffect.NavigateToSeriesDetails(mediaId))
        }
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

    override fun onRetryClick() {
        loadHomeScreenData()
    }


    override fun onCollectionClick(collectionId: Int, collectionName: String) {
        sendEffect(
            HomeEffect.NavigateToCollection(
                collectionId = collectionId,
                collectionName = collectionName
            )
        )
    }

    override fun onFeaturesCollectionClicked(sectionType: CategoryMediaSectionType) {
        sendEffect(
            HomeEffect.NavigateToCategoryMediaSection(
                sectionType = sectionType
            )
        )
    }

    override fun onSeeMoreClicked(sectionType: CategoryMediaSectionType) {
        sendEffect(
            HomeEffect.NavigateToCategoryMediaSection(
                sectionType = sectionType
            )
        )
    }
}