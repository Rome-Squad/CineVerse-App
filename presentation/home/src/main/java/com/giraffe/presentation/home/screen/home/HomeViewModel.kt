package com.giraffe.presentation.home.screen.home

import androidx.lifecycle.viewModelScope
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.ObservePopularMoviesUseCase
import com.giraffe.media.movie.usecase.genre.ObserveMoviesGenresUseCase
import com.giraffe.media.movie.usecase.matchesYourVibe.ObserveMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyReleased.ObserveRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.upcoming.ObserveUpcomingMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.ObservePopularSeriesUseCase
import com.giraffe.media.series.usecase.matchesYourVibe.ObserveMatchesYourVibeSeriesUseCase
import com.giraffe.media.series.usecase.recentlyReleased.ObserveRecentlyReleasedSeriesUseCase
import com.giraffe.media.series.usecase.recentlyViewed.ObserveRecentlyViewedSeriesUseCase
import com.giraffe.media.series.usecase.topRated.ObserveTopRatedSeriesUseCase
import com.giraffe.presentation.home.base.BaseViewModel
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.model.PopularMediaUi
import com.giraffe.presentation.home.model.Poster
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.utils.toPopularMediaUi
import com.giraffe.presentation.home.utils.toPoster
import com.giraffe.presentation.home.utils.toUi
import com.giraffe.user.usecase.GetUserNameUseCase
import com.giraffe.user.usecase.IsLoggedInByAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val observeRecentlyViewedMoviesUseCase: ObserveRecentlyViewedMoviesUseCase,
    private val observeRecentlyViewedSeriesUseCase: ObserveRecentlyViewedSeriesUseCase,
    private val observeMatchesYourVibeMoviesUseCase: ObserveMatchesYourVibeMoviesUseCase,
    private val observeMatchesYourVibeSeriesUseCase: ObserveMatchesYourVibeSeriesUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val isLoggedInByAccountUseCase: IsLoggedInByAccountUseCase,
    private val observeMoviesGenresUseCase: ObserveMoviesGenresUseCase
) : BaseViewModel<HomeScreenState, HomeEffect>(initialState = HomeScreenState()),
    HomeInteractionListener {

    init {
        getUserName()
        getMoviesGenres()
        getRecentlyReleased()
        getUpcomingMovies()
        getYourCollections()
        getTopRatedSeries()
        getMatchesYourVibe()
        getRecentlyViewed()
    }

    private fun getUserName() {
        safeExecute(
            onSuccess = {
                if (it) safeCollect(
                    onEmitNewValue = ::onGetUseNameSuccess,
                    onError = ::onError,
                    block = getUserNameUseCase::invoke
                )
            },
            block = isLoggedInByAccountUseCase::invoke
        )
    }

    private fun onGetUseNameSuccess(userName: String) = updateState { it.copy(userName = userName) }

    private fun getMoviesGenres() {
        safeCollect(
            onEmitNewValue = ::onGetMoviesGenresSuccess,
            onError = ::onError.also { getPopularity() },
            block = observeMoviesGenresUseCase::invoke
        )
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(moviesGenres = genres) }
        getPopularity()
    }

    private fun getPopularity() {
        updateState { it.copy(isLoadingPopularity = true) }
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

    private fun onGetPopularityMoviesSuccess(movies: List<Movie>) {
        val newMovies = movies.map { movie -> movie.toPopularMediaUi(state.value.moviesGenres) }
        updatePopularityMedia(newMovies)
    }

    private fun onGetPopularitySeriesSuccess(series: List<Series>) {
        val newSeries =
            series.map { seriesList -> seriesList.toPopularMediaUi(state.value.seriesGenres) }
        updatePopularityMedia(newSeries)
    }

    private fun updatePopularityMedia(items: List<PopularMediaUi>) {
        if (items.isNotEmpty()) viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
            updateState { currentState ->
                currentState.copy(
                    popularity = (items + currentState.popularity).distinctBy { it.id },
                    isLoadingPopularity = false
                )
            }
        }
    }

    private fun getRecentlyReleased() {
        updateState { it.copy(isLoadingRecentlyReleased = true) }
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

    private fun onGetRecentlyReleasedMoviesSuccess(movies: List<Movie>) {
        updateRecentlyReleasedPosters(movies.map(Movie::toPoster))
    }

    private fun onGetRecentlyReleasedSeriesSuccess(series: List<Series>) {
        updateRecentlyReleasedPosters(series.map(Series::toPoster))
    }

    private fun updateRecentlyReleasedPosters(posters: List<Poster>) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
            updateState { currentState ->
                currentState.copy(
                    recentlyReleased = (posters + currentState.recentlyReleased).distinctBy { it.id },
                    isLoadingRecentlyReleased = false
                )
            }
        }
    }

    private fun getUpcomingMovies() {
        updateState { it.copy(isLoadingUpcomingMovies = true) }
        safeCollect(
            onEmitNewValue = ::onGetUpcomingMoviesSuccess,
            onError = ::onError,
            block = observeUpcomingMoviesUseCase::invoke
        )
    }

    private fun onGetUpcomingMoviesSuccess(movies: List<Movie>) {
        if (movies.isNotEmpty()) viewModelScope.launch(Dispatchers.IO) {
            delay(5000L)
            updateState { currentState ->
                currentState.copy(
                    upcomingMovies = (movies.map(Movie::toPoster) + currentState.upcomingMovies).distinctBy { it.id },
                    isLoadingUpcomingMovies = false
                )
            }
        }
    }

    private fun getYourCollections() {
        safeCollect(
            onEmitNewValue = ::onGetYourCollectionsSuccess,
            onError = ::onError,
            block = getCollectionsUseCase::invoke
        )
    }

    private fun onGetYourCollectionsSuccess(collections: List<Collection>) {
        updateState {
            it.copy(
                yourCollections = collections.map(Collection::toUi)
            )
        }
    }

    private fun getTopRatedSeries() {
        updateState { it.copy(isLoadingTopRatedSeries = true) }
        safeCollect(
            onEmitNewValue = ::onGetTopRatedSeriesSuccess,
            onError = ::onError,
            block = observeTopRatedSeriesUseCase::invoke
        )
    }

    private fun onGetTopRatedSeriesSuccess(series: List<Series>) {
        if (series.isNotEmpty()) viewModelScope.launch(Dispatchers.IO) {
            delay(5000L)
            updateState { currentState ->
                currentState.copy(
                    topRated = (series.map(Series::toPoster) + currentState.topRated).distinctBy { it.id },
                    isLoadingTopRatedSeries = false
                )
            }
        }
    }

    private fun getMatchesYourVibe() {
        updateState { it.copy(isLoadingMatchesYourVibe = true) }
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
        updateMatchesYourVibePosters(movies.map(Movie::toPoster))
    }

    private fun onGetMatchesYourVibeSeriesSuccess(series: List<Series>) {
        updateMatchesYourVibePosters(series.map(Series::toPoster))
    }

    private fun updateMatchesYourVibePosters(posters: List<Poster>) {
        if (posters.isNotEmpty()) viewModelScope.launch(Dispatchers.IO) {
            delay(5000L)
            updateState { currentState ->
                currentState.copy(
                    matchVibes = (posters + currentState.matchVibes).distinctBy { it.id },
                    isLoadingMatchesYourVibe = false
                )
            }
        }
    }

    private fun getRecentlyViewed() {
        updateState { it.copy(isLoadingRecentlyViewed = true) }
        safeCollect(
            onEmitNewValue = ::onGetRecentlyMoviesSuccess,
            onError = ::onError,
            block = observeRecentlyViewedMoviesUseCase::invoke
        )
        safeCollect(
            onEmitNewValue = ::onGetRecentlySeriesSuccess,
            onError = ::onError,
            block = observeRecentlyViewedSeriesUseCase::invoke
        )
    }

    private fun onGetRecentlyMoviesSuccess(movies: List<Movie>) {
        updateRecentPosters(movies.map(Movie::toPoster))
    }

    private fun onGetRecentlySeriesSuccess(series: List<Series>) {
        updateRecentPosters(series.map(Series::toPoster))
    }

    private fun updateRecentPosters(posters: List<Poster>) {
        if (posters.isNotEmpty()) viewModelScope.launch(Dispatchers.IO) {
            delay(5000L)
            updateState {
                it.copy(
                    recentlyViewed = (posters + it.recentlyViewed)
                        .distinctBy { poster -> poster.id }
                        .sortedByDescending { poster -> poster.recentViewedAt }
                        .take(20),
                    isLoadingRecentlyViewed = false
                )
            }
        }
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

    private fun onError(error: Throwable) = updateState {
        it.copy(isNoInternet = error is NoInternetException)
    }
}