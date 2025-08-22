package com.giraffe.presentation.home.screen.home

import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.ObservePopularMoviesUseCase
import com.giraffe.media.movie.usecase.genre.ObserveMoviesGenresUseCase
import com.giraffe.media.movie.usecase.matchesYourVibe.ObserveMatchesYourVibeMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyReleased.ObserveRecentlyReleasedMoviesUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.ObserveRecentlyViewedMoviesUseCase
import com.giraffe.media.movie.usecase.upcoming.ObserveUpcomingMoviesUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.ObservePopularSeriesUseCase
import com.giraffe.media.series.usecase.genre.ObserveSeriesGenresUseCase
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
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import com.giraffe.user.usecase.GetUserNameUseCase
import com.giraffe.user.usecase.IsLoggedInByAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val observeMoviesGenresUseCase: ObserveMoviesGenresUseCase,
    private val observeSeriesGenresUseCase: ObserveSeriesGenresUseCase,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase
) : BaseViewModel<HomeScreenState, HomeEffect>(initialState = HomeScreenState()),
    HomeInteractionListener {

    init {
        observeContentPreference()
        getUserName()
        getGenres()
        getRecentlyReleased()
        getUpcoming()
        getYourCollections()
        getTopRated()
        getMatchesYourVibe()
        getRecentlyViewed()
    }

    private fun getUserName() {
        safeExecute(
            onSuccess = {
                if (it) safeCollect(
                    onEmitNewValue = ::onGetUseNameSuccess,
                    block = getUserNameUseCase::invoke
                )
            },
            block = { isLoggedInByAccountUseCase() }
        )
    }

    private fun onGetUseNameSuccess(userName: String) = updateState { it.copy(userName = userName) }

    private fun getGenres() {
        safeCollect(
            onEmitNewValue = ::onGetMoviesGenresSuccess,
            onError = { getPopularityMovies() },
            block = observeMoviesGenresUseCase::invoke
        )
        safeCollect(
            onEmitNewValue = ::onGetSeriesGenresSuccess,
            onError = { getPopularitySeries() },
            block = observeSeriesGenresUseCase::invoke
        )
    }

    private fun onGetMoviesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(moviesGenres = genres) }
        getPopularityMovies()
    }

    private fun onGetSeriesGenresSuccess(genres: List<Genre>) {
        updateState { it.copy(seriesGenres = genres) }
        getPopularitySeries()
    }

    private fun getPopularityMovies() {
        updateState { it.copy(isLoadingPopularity = true, hasPopularityError = false) }
        safeCollect(
            onEmitNewValue = ::onGetPopularityMoviesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingPopularity = false,
                        hasPopularityError = true
                    )
                }
            },
            block = observePopularMoviesUseCase::invoke
        )
    }

    private fun onGetPopularityMoviesSuccess(movies: List<Movie>) {
        val newMovies = movies.map { movie -> movie.toPopularMediaUi(state.value.moviesGenres) }
        updatePopularityMedia(newMovies)
    }

    private fun getPopularitySeries() {
        updateState { it.copy(isLoadingPopularity = true) }
        safeCollect(
            onEmitNewValue = ::onGetPopularitySeriesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingPopularity = false,
                        hasPopularityError = true
                    )
                }
            },
            block = observePopularSeriesUseCase::invoke
        )
    }

    private fun onGetPopularitySeriesSuccess(series: List<Series>) {
        val newSeries =
            series.map { seriesList -> seriesList.toPopularMediaUi(state.value.seriesGenres) }
        updatePopularityMedia(newSeries)
    }

    private fun updatePopularityMedia(items: List<PopularMediaUi>) {
        if (items.isNotEmpty()) updateState {
            it.copy(
                popularity = (items + it.popularity).distinctBy { item -> item.id },
                isLoadingPopularity = false
            )
        }
    }

    override fun getRecentlyReleased() {
        updateState { it.copy(isLoadingRecentlyReleased = true, hasRecentlyReleasedError = false) }
        safeCollect(
            onEmitNewValue = ::onGetRecentlyReleasedMoviesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingRecentlyReleased = false,
                        hasRecentlyReleasedError = true
                    )
                }
            },
            block = observeRecentlyReleasedMoviesUseCase::invoke
        )
        safeCollect(
            onEmitNewValue = ::onGetRecentlyReleasedSeriesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingRecentlyReleased = false,
                        hasRecentlyReleasedError = true
                    )
                }
            },
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
        if (posters.isNotEmpty()) updateState {
            it.copy(
                recentlyReleased = (posters + it.recentlyReleased).distinctBy { item -> item.id },
                isLoadingRecentlyReleased = false
            )
        }
    }

    override fun getUpcoming() {
        updateState { it.copy(isLoadingUpcoming = true, hasUpcomingError = false) }
        safeCollect(
            onEmitNewValue = ::onGetUpcomingMoviesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingUpcoming = false,
                        hasUpcomingError = true
                    )
                }
            },
            block = observeUpcomingMoviesUseCase::invoke
        )
    }

    private fun onGetUpcomingMoviesSuccess(movies: List<Movie>) {
        if (movies.isNotEmpty()) updateState {
            it.copy(
                upcomingMovies = (movies.map(Movie::toPoster) + it.upcomingMovies).distinctBy { item -> item.id },
                isLoadingUpcoming = false
            )
        }
    }

    private fun getYourCollections() {
        safeCollect(
            onEmitNewValue = ::onGetYourCollectionsSuccess,
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

    override fun getTopRated() {
        updateState { it.copy(isLoadingTopRated = true, hasTopRatedError = false) }
        safeCollect(
            onEmitNewValue = ::onGetTopRatedSeriesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingTopRated = false,
                        hasTopRatedError = true
                    )
                }
            },
            block = observeTopRatedSeriesUseCase::invoke
        )
    }

    private fun onGetTopRatedSeriesSuccess(series: List<Series>) {
        if (series.isNotEmpty()) updateState {
            it.copy(
                topRated = (series.map(Series::toPoster) + it.topRated).distinctBy { item -> item.id },
                isLoadingTopRated = false
            )
        }
    }

    override fun getMatchesYourVibe() {
        updateState { it.copy(isLoadingMatchesYourVibe = true, hasMatchesYourVibeError = false) }
        safeCollect(
            onEmitNewValue = ::onGetMatchesYourVibeMoviesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingMatchesYourVibe = false,
                        hasMatchesYourVibeError = true
                    )
                }
            },
            block = observeMatchesYourVibeMoviesUseCase::invoke
        )
        safeCollect(
            onEmitNewValue = ::onGetMatchesYourVibeSeriesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingMatchesYourVibe = false,
                        hasMatchesYourVibeError = true
                    )
                }
            },
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
        if (posters.isNotEmpty()) updateState {
            it.copy(
                matchVibes = (posters + it.matchVibes).distinctBy { item -> item.id },
                isLoadingMatchesYourVibe = false
            )
        }
    }

    override fun getRecentlyViewed() {
        updateState { it.copy(isLoadingRecentlyViewed = true, hasRecentlyViewedError = false) }
        safeCollect(
            onEmitNewValue = ::onGetRecentlyMoviesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingRecentlyViewed = false,
                        hasRecentlyViewedError = true
                    )
                }
            },
            block = observeRecentlyViewedMoviesUseCase::invoke
        )
        safeCollect(
            onEmitNewValue = ::onGetRecentlySeriesSuccess,
            onError = {
                updateState {
                    it.copy(
                        isLoadingRecentlyViewed = false,
                        hasRecentlyViewedError = true
                    )
                }
            },
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

    private fun observeContentPreference() {
        safeCollect(
            onEmitNewValue = { preference ->
                updateState { it.copy(contentPreference = preference) }
            },
            block = getContentPreferenceUseCase::invoke
        )
    }
}