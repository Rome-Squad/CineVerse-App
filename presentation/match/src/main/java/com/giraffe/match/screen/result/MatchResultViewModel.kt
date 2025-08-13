package com.giraffe.match.screen.result

import androidx.lifecycle.SavedStateHandle
import com.giraffe.match.base.BaseViewModel
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.collections.usecase.AddCollectionUseCase
import com.giraffe.media.collections.usecase.AddMovieToCollectionUseCase
import com.giraffe.media.collections.usecase.GetCollectionsUseCase
import com.giraffe.media.match.usecase.GetMatchingMoviesUseCase
import com.giraffe.media.match.usecase.GetMatchingSeriesUseCase
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetMovieDetailsUseCase
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.user.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchResultViewModel @Inject constructor(
    private val getMatchingMoviesUseCase: GetMatchingMoviesUseCase,
    private val getMatchingSeriesUseCase: GetMatchingSeriesUseCase,
    private val getMoviesGenresByIdsUseCase: GetMoviesGenresByIdsUseCase,
    private val getSeriesGenresByIdsUseCase: GetSeriesGenresByIdsUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val addCollectionUseCase: AddCollectionUseCase,
    private val addMovieToCollectionUseCase: AddMovieToCollectionUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MatchResultScreenState, MatchResultScreenEffect>(MatchResultScreenState()),
    MatchResultInteractionListener {

    init {
        val genreIds = savedStateHandle.get<List<Int>>("genreSelections")
            ?: savedStateHandle.get<IntArray>("selectedGenres")?.toList()
            ?: emptyList()
        val runtime = savedStateHandle.get<Int>("minRuntime")
            ?: savedStateHandle.get<Int>("timeSelection")
        val releaseDate = savedStateHandle.get<String>("earliestDate")
            ?: savedStateHandle.get<String>("releasePeriodSelection")

        val (minRuntime, maxRuntime) = mapTimeSelectionToRuntimeRange(runtime)
        val (earliestDate, latestDate) = mapReleasePeriodToDateRange(releaseDate)

        updateState {
            it.copy(
                genreIds = genreIds,
                runtime = runtime,
                releaseDate = releaseDate,
                minRuntime = minRuntime,
                maxRuntime = maxRuntime,
                earliestDate = earliestDate,
                latestDate = latestDate
            )
        }

        checkLoginStatus()

        loadMatchingResults(
            genreIds = genreIds,
            minRuntime = minRuntime,
            maxRuntime = maxRuntime,
            earliestDate = earliestDate,
            latestDate = latestDate
        )
    }

    private fun loadMatchingResults(
        genreIds: List<Int>,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestDate: String?,
        latestDate: String?
    ) {
        updateState { it.copy(isLoading = true) }

        safeExecute(
            onSuccess = ::handleLoadResultsSuccess,
            onError = ::onError,
            block = {
                val genresQuery = genreIds.joinToString(",")

                val movies = getMatchingMoviesUseCase(
                    genreIds = genresQuery,
                    minRuntime = minRuntime,
                    maxRuntime = maxRuntime,
                    earliestFirstAirDate = earliestDate,
                    latestFirstAirDate = latestDate
                ).take(10)
                val movieItems = mapMoviesToMatchResult(movies)

                val seriesList = getMatchingSeriesUseCase(
                    genreIds = genresQuery,
                    minRuntime = minRuntime,
                    maxRuntime = maxRuntime,
                    earliestFirstAirDate = earliestDate,
                    latestFirstAirDate = latestDate
                ).take(10)
                val seriesItems = mapSeriesToMatchResult(seriesList)

                Pair(movieItems, seriesItems)
            }
        )
    }

    private fun handleLoadResultsSuccess(data: Pair<List<MatchResultModel>, List<MatchResultModel>>) {
        val (movieItems, seriesItems) = data
        updateState {
            it.copy(
                matchItems = movieItems + seriesItems,
                isLoading = false
            )
        }
    }

    private suspend fun mapMoviesToMatchResult(movies: List<Movie>): List<MatchResultModel> {
        return movies.mapNotNull { movie ->
            try {
                val details = getMovieDetailsUseCase(movie.id)
                val genres = getMoviesGenresByIdsUseCase(details.genresID).map { it.title }
                details.toMatchResultModel(genres)
            } catch (_: Exception) {
                null
            }
        }
    }

    private suspend fun mapSeriesToMatchResult(seriesList: List<Series>): List<MatchResultModel> {
        return seriesList.mapNotNull { series ->
            try {
                val details = getSeriesDetailsUseCase(series.id)
                val genres = getSeriesGenresByIdsUseCase(details.genreIDs).map { it.title }
                details.toMatchResultModel(genres, details.rating, details.youtubeVideoId)
            } catch (_: Exception) {
                null
            }
        }
    }

    override fun onRetryClick() {
        with(state.value) {
            loadMatchingResults(
                genreIds = genreIds,
                minRuntime = minRuntime,
                maxRuntime = maxRuntime,
                earliestDate = earliestDate,
                latestDate = latestDate
            )
        }
    }

    override fun onAddToCollection(itemId: Int, mediaType: MediaType) {
        updateState {
            it.copy(
                currentSelectedItemId = itemId,
                currentSelectedMediaType = mediaType
            )
        }

        executeIfLoggedIn(
            block = {
                safeExecute(
                    onSuccess = ::handleGetCollectionsSuccess,
                    onError = ::onError,
                    block = { getCollectionsUseCase() }
                )
            },
            ifNotLoggedIn = {
                updateState { it.copy(isVisibleLoginBottomSheet = true) }
            }
        )
    }

    private fun handleGetCollectionsSuccess(collections: List<Collection>) {
        updateState {
            it.copy(
                collections = collections.map { it.toUi() },
                collectionBottomSheet = if (collections.isNotEmpty()) {
                    MatchResultScreenState.CollectionBottomSheet.AddToCollection
                } else {
                    MatchResultScreenState.CollectionBottomSheet.NoCollections
                },
                isLoading = false
            )
        }
    }


    override fun onCollectionBottomSheetDismiss() {
        updateState {
            it.copy(
                collectionBottomSheet = null,
                collections = emptyList(),
                newCollectionName = "",
                currentSelectedItemId = null,
                currentSelectedMediaType = null
            )
        }
    }

    override fun onLoginButtonClick() {
        updateState { it.copy(isVisibleLoginBottomSheet = false) }
        sendEffect(MatchResultScreenEffect.NavigateToLogin)
    }

    override fun onBackClick() {
        sendEffect(MatchResultScreenEffect.NavigateBack)
    }

    override fun onItemPosterClick(itemId: Int, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(MatchResultScreenEffect.NavigateToMovieDetails(itemId))
            MediaType.SERIES -> sendEffect(MatchResultScreenEffect.NavigateToSeriesDetails(itemId))
        }
    }

    override fun onPlayButtonClick(youtubeVideoId: String) {
        sendEffect(MatchResultScreenEffect.NavigateToYouTubePlayer(youtubeVideoId))
    }

    fun onCarouselPageChanged(newPage: Int) {
        updateState { it.copy(currentCarouselPage = newPage) }
    }

    override fun onCreateCollectionButtonClick() {
        updateState {
            it.copy(collectionBottomSheet = MatchResultScreenState.CollectionBottomSheet.CreateCollection)
        }
    }

    override fun onNewCollectionNameChange(name: String) {
        updateState { it.copy(newCollectionName = name) }
    }

    override fun onCancelCreateNewCollectionClick() {
        updateState {
            it.copy(
                collectionBottomSheet = MatchResultScreenState.CollectionBottomSheet.AddToCollection,
                newCollectionName = ""
            )
        }
    }

    override fun onConfirmCreateNewCollectionClick() {
        val newName = state.value.newCollectionName.trim()
        safeExecute(
            onSuccess = {
                val collections = getCollectionsUseCase()
                updateState {
                    it.copy(
                        collections = collections.map { it.toUi() },
                        collectionBottomSheet = MatchResultScreenState.CollectionBottomSheet.AddToCollection,
                        newCollectionName = "",
                        isLoading = false
                    )
                }
            },
            onError = { throwable, _ ->
                updateState { it.copy(isLoading = false) }
                sendEffect(MatchResultScreenEffect.ShowError(throwable))
            },
            block = {
                updateState { it.copy(isLoading = true) }
                addCollectionUseCase(
                    Collection(id = 0, name = newName, description = "", itemsCount = 0)
                )
            }
        )
    }

    override fun onDismissLoginBottomSheet() {
        updateState { it.copy(isVisibleLoginBottomSheet = false) }
    }

    override fun navigateToMoviesDetailsScreen(movieId: Int) {
        sendEffect(MatchResultScreenEffect.NavigateToMovieDetails(movieId))
    }

    override fun navigateToSeriesDetailsScreen(seriesId: Int) {
        sendEffect(MatchResultScreenEffect.NavigateToSeriesDetails(seriesId))
    }

    override fun navigateToYouTubePlayer(videoId: String) {
        sendEffect(MatchResultScreenEffect.NavigateToYouTubePlayer(videoId))
    }

    override fun onCollectionClick(collectionId: Int) {
        val itemId = state.value.currentSelectedItemId ?: return

        safeExecute(
            onSuccess = { handleCollectionSuccess(collectionId) },
            onError = { throwable, isNoInternet ->
                handleCollectionError(
                    collectionId,
                    throwable,
                    isNoInternet
                )
            },
            block = {
                updateState {
                    it.copy(
                        collections = it.collections.map { coll ->
                            if (coll.id == collectionId) coll.copy(isLoading = true) else coll
                        }
                    )
                }
                addMovieToCollectionUseCase(collectionId, itemId)
            }
        )
    }

    private fun handleCollectionSuccess(collectionId: Int) {
        updateState {
            it.copy(
                collections = it.collections.map { coll ->
                    if (coll.id == collectionId) coll.copy(isLoading = false) else coll
                },
                collectionBottomSheet = null
            )
        }
    }

    private fun handleCollectionError(
        collectionId: Int,
        throwable: Throwable,
        isNoInternet: Boolean
    ) {
        updateState {
            it.copy(
                collections = it.collections.map { coll ->
                    if (coll.id == collectionId) coll.copy(isLoading = false) else coll
                },
                isNoInternet = isNoInternet
            )
        }
        sendEffect(MatchResultScreenEffect.ShowError(throwable))
    }

    private fun executeIfLoggedIn(
        block: () -> Unit,
        ifNotLoggedIn: () -> Unit = { updateState { it.copy(isVisibleLoginBottomSheet = true) } }
    ) {
        safeExecute(
            block = {
                val loggedIn = isLoggedInUseCase()
                if (loggedIn) block() else ifNotLoggedIn()
            },
            onError = { _, _ ->
                ifNotLoggedIn()
            }
        )
    }



    private fun onError(error: Throwable, isNoInternet: Boolean) {
        updateState { it.copy(isLoading = false, isNoInternet = isNoInternet) }
        sendEffect(MatchResultScreenEffect.ShowError(error))
    }

    private fun mapTimeSelectionToRuntimeRange(timeSelection: Int?): Pair<Int?, Int?> {
        return when (timeSelection) {
            1 -> Pair(null, 90)
            2 -> Pair(90, 120)
            3 -> Pair(120, null)
            else -> Pair(null, null)
        }
    }

    private fun mapReleasePeriodToDateRange(releasePeriod: String?): Pair<String?, String?> {
        return when (releasePeriod) {
            "Recent" -> Pair("2001-01-01", null)
            "Classic" -> Pair(null, "2000-12-31")
            else -> Pair(null, null)
        }
    }

    private fun checkLoginStatus() {
        safeExecute {
            val loggedIn = isLoggedInUseCase()
            updateState { it.copy(isLoggedIn = loggedIn) }
        }
    }
}
