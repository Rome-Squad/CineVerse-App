package com.giraffe.home.screen.home

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.giraffe.home.R
import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.utils.toHomeUiModel
import com.giraffe.home.utils.toPopularMediaUiModel
import com.giraffe.media.exception.AccessDeniedException
import com.giraffe.media.exception.MediaException
import com.giraffe.media.exception.NotFoundException
import com.giraffe.media.exception.UnknownException
import com.giraffe.media.exception.ValidationException
import com.giraffe.media.home.usecase.GetPopularityMoviesUseCase
import com.giraffe.media.home.usecase.GetPopularitySeriesUseCase
import com.giraffe.media.home.usecase.GetRecentlyReleasedMoviesUseCase
import com.giraffe.media.home.usecase.GetRecentlyReleasedSeriesUseCase
import com.giraffe.media.home.usecase.GetTopRatedSeriesUseCase
import com.giraffe.media.home.usecase.GetUpcomingMoviesUseCase
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetRecentlyMoviesUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import com.giraffe.media.series.usecase.GetRecentSeriesUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    }

//    private fun handler(): CoroutineExceptionHandler {
//        return CoroutineExceptionHandler { _, throwable ->
//            Log.d("Throw", throwable.message.toString())
//        }
//    }

    private fun loadHomeScreen() {
        updateState { it.copy(isLoading = true, isError = false) }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val popularMoviesDeferred = async { getPopularityMoviesUseCase() }
                val popularSeriesDeferred = async { getPopularitySeriesUseCase() }
                val recentMoviesDeferred = async { getRecentlyReleasedMoviesUseCase() }
                val recentSeriesDeferred = async { getRecentlyReleasedSeriesUseCase() }
                val recentlyViewedMoviesDeferred = async { getRecentlyMoviesUseCase() }
                val recentlyViewedSeriesDeferred = async { getRecentlySeriesUseCase() }
                val recentlyViewedMovies = recentlyViewedMoviesDeferred.await()
                val recentlyViewedSeries = recentlyViewedSeriesDeferred.await()
                val popularMovies = popularMoviesDeferred.await()
                val popularSeries = popularSeriesDeferred.await()
                val recentMovies = recentMoviesDeferred.await()
                val recentSeries = recentSeriesDeferred.await()


                val recommendedSeries = recentlyViewedSeries.firstOrNull()
                    ?.let { getRecommendedSeriesUseCase(it.id.toLong(), 1) } ?: emptyList()
                val recommendedMovies = recentlyViewedMovies.firstOrNull()
                    ?.let { getRecommendedMovieUseCase(it.id, 1) } ?: emptyList()

                val recommendedSeriesUi = recommendedSeries.map { it.toHomeUiModel() }
                val recommendedMoviesUi = recommendedMovies.map { it.toHomeUiModel() }
                val recentlyViewedMovieUi = recentlyViewedMovies.map { it.toHomeUiModel() }
                val recentlyViewedSeriesUi = recentlyViewedSeries.map { it.toHomeUiModel() }

                val recentlyViewed = recentlyViewedSeriesUi + recentlyViewedMovieUi
                val pickedForYou = recommendedSeriesUi + recommendedMoviesUi


                val topRated = getTopRatedSeriesUseCase()
                val upcoming = getUpcomingMoviesUseCase()

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
                        matchVibes = pickedForYou,
                        popularity = popularMovieUi + popularSeriesUi,
                        recentlyReleased = recentMovieUi + recentSeriesUi,
                        topRated = topRatedUi,
                        upcomingMovies = upcomingUi,
                        recentlyViewed = recentlyViewed
                    )
                }

            } catch (e: MediaException) {
                val errorResId = mapExceptionToStringRes(e)
                updateState { it.copy(isLoading = false, isError = true) }
                sendEffect(HomeEffect.ShowError(errorResId))
            } catch (e: Exception) {
                updateState { it.copy(isLoading = false, isError = true) }
//                sendEffect(HomeEffect.ShowError(e.message))
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

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(HomeEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(HomeEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun onCollectionClicked(collectionId: Int, type: CollectionClickType) {
        when (type) {
            CollectionClickType.YOUR_COLLECTION -> sendEffect(
                HomeEffect.NavigateToYourCollectionDetails(
                    collectionId
                )
            )

            CollectionClickType.FEATURED -> sendEffect(
                HomeEffect.NavigateToFeaturedCollectionDetails(
                    collectionId
                )
            )
        }
    }

    override fun onSeeAllPopularClicked() {
        sendEffect(HomeEffect.NavigateToPopularList)
    }

    override fun onSeeAllRecentlyReleasedClicked() {
        sendEffect(HomeEffect.NavigateToRecentlyReleasedList)
    }

    override fun onSeeAllTopRatedClicked() {
        sendEffect(HomeEffect.NavigateToTopRatedList)
    }

    override fun onSeeAllUpcomingClicked() {
        sendEffect(HomeEffect.NavigateToUpcomingList)
    }

    override fun onSeeAllRecentlyViewedClicked() {
        sendEffect(HomeEffect.NavigateToRecentlyViewedList)
    }

    override fun onSeeAllYourCollection() {
        sendEffect(HomeEffect.NavigateToAllYourCollections)
    }

    override fun onWhatShouldIWatchClicked() {
        sendEffect(HomeEffect.NavigateToRecommendedList)
    }

    override fun onNeedMoreToWatchClicked() {
        sendEffect(HomeEffect.NavigateToExploreMore)
    }
}