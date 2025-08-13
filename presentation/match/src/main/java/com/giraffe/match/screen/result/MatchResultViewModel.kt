package com.giraffe.match.screen.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
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

    private var _genreIds: List<Int> = emptyList()
    private var _runtime: Int? = null
    private var _releaseDate: String? = null
    private var minRt: Int? = null
    private var maxRt: Int? = null
    private var earliest: String? = null
    private var latest: String? = null

    private var currentSelectedItemId: Int? = null
    private var currentSelectedMediaType: MediaType? = null

    init {
        _genreIds = savedStateHandle.get<List<Int>>("genreSelections")
            ?: savedStateHandle.get<IntArray>("selectedGenres")?.toList() ?: emptyList()
        _runtime = savedStateHandle.get<Int>("minRuntime")
            ?: savedStateHandle.get<Int>("timeSelection")
        _releaseDate = savedStateHandle.get<String>("earliestDate")
            ?: savedStateHandle.get<String>("releasePeriodSelection")

        val (minRuntime, maxRuntime) = mapTimeSelectionToRuntimeRange(_runtime)
        val (earliestDate, latestDate) = mapReleasePeriodToDateRange(_releaseDate)

        minRt = minRuntime
        maxRt = maxRuntime
        earliest = earliestDate
        latest = latestDate

        checkLoginStatus()

        loadMatchingResults(
            genreIds = _genreIds,
            minRuntime = minRt,
            maxRuntime = maxRt,
            earliestDate = earliest,
            latestDate = latest
        )
    }

    private fun loadMatchingResults(
        genreIds: List<Int>,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestDate: String?,
        latestDate: String?
    ) {
        updateState { it.copy(isLoading = true, isError = false) }

        safeExecute(
            onSuccess = { data ->
                val (movieItems, seriesItems) = data
                updateState {
                    it.copy(
                        matchItems = movieItems + seriesItems,
                        isLoading = false
                    )
                }
            },
            onError = { throwable, isNoInternet ->
                onError(throwable, isNoInternet)
            },
            block = {
                val genresQuery = genreIds.joinToString(separator = ",")

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

    private suspend fun mapMoviesToMatchResult(movies: List<Movie>): List<MatchResultModel> {
        return movies.mapNotNull { movie ->
            try {
                val details = getMovieDetailsUseCase(movie.id)
                val genres = getMoviesGenresByIdsUseCase(details.genresID).map { it.title }
                details.toMatchResultModel(genres)
            } catch (e: Exception) {
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
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun onRetryClick() {
        loadMatchingResults(
            genreIds = _genreIds,
            minRuntime = minRt,
            maxRuntime = maxRt,
            earliestDate = earliest,
            latestDate = latest
        )
    }


    override fun onAddToCollection(itemId: Int, mediaType: MediaType) {
        currentSelectedItemId = itemId
        currentSelectedMediaType = mediaType

        executeIfLoggedIn(
            block = {
                safeExecute(
                    onSuccess = { collections ->
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
                    },
                    onError = { throwable, _ ->
                        updateState { it.copy(isLoading = false) }
                        sendEffect(MatchResultScreenEffect.ShowError(throwable))
                    },
                    block = {
                        getCollectionsUseCase()
                    }
                )
            },
            ifNotLoggedIn = {
                updateState { it.copy(isVisibleLoginBottomSheet = true) }
            }
        )
    }

    override fun onCollectionBottomSheetDismiss() {
        updateState {
            it.copy(
                collectionBottomSheet = null,
                collections = emptyList(),
                newCollectionName = ""
            )
        }
        currentSelectedItemId = null
        currentSelectedMediaType = null
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

    override fun onCollectionClick(collectionId: Int) {
        val itemId = currentSelectedItemId ?: return
        val mediaType = currentSelectedMediaType ?: return

        safeExecute(
            onSuccess = {
                updateState {
                    it.copy(
                        collections = it.collections.map { coll ->
                            if (coll.id == collectionId) coll.copy(isLoading = false) else coll
                        },
                        collectionBottomSheet = null
                    )
                }
            },
            onError = { throwable, _ ->
                updateState {
                    it.copy(
                        collections = it.collections.map { coll ->
                            if (coll.id == collectionId) coll.copy(isLoading = false) else coll
                        }
                    )
                }
                sendEffect(MatchResultScreenEffect.ShowError(throwable))
            },
            block = {
                updateState {
                    it.copy(
                        collections = it.collections.map { coll ->
                            if (coll.id == collectionId) coll.copy(isLoading = true) else coll
                        }
                    )
                }

                if (mediaType == MediaType.MOVIE) {
                    addMovieToCollectionUseCase(collectionId, itemId)
                } else {
                    addMovieToCollectionUseCase(collectionId, itemId)
                }
            }
        )
    }

    private fun executeIfLoggedIn(
        block: () -> Unit,
        ifNotLoggedIn: () -> Unit = { updateState { it.copy(isVisibleLoginBottomSheet = true) } }
    ) {
        viewModelScope.launch {
            try {
                val loggedIn = isLoggedInUseCase()
                if (loggedIn) {
                    block()
                } else {
                    ifNotLoggedIn()
                }
            } catch (e: Exception) {
                ifNotLoggedIn()
            }
        }
    }

    private fun onError(error: Throwable, isNoInternet: Boolean) {
        updateState {
            it.copy(isLoading = false, isError = true, isNetworkError = isNoInternet)
        }
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
        viewModelScope.launch {
            val loggedIn = isLoggedInUseCase()
            updateState { it.copy(isLoggedIn = loggedIn) }
        }
    }

}
