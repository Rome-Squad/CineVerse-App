package com.giraffe.presentation.details.screens.seriesdetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.GetMediaMembersBySeriesIdUseCase
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.AddSeriesRatingUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeasonsUseCase
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase
import com.giraffe.media.series.usecase.GetUserSeriesRatingUseCase
import com.giraffe.media.series.usecase.genre.GetSeriesGenresByIdsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.model.SeriesUi
import com.giraffe.presentation.details.navigation.routes.SeriesDetailsRoute
import com.giraffe.presentation.details.utils.groupByRole
import com.giraffe.presentation.details.utils.toCastUi
import com.giraffe.presentation.details.utils.toCrewUi
import com.giraffe.presentation.details.utils.toUi
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import com.giraffe.user.usecase.IsLoggedInByAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.giraffe.user.exception.NoInternetException as UserNoInternetException

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val getSeriesDetails: GetSeriesDetailsUseCase,
    private val getLastSeasons: GetSeasonsUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    private val getCastAndCrewOfSeries: GetMediaMembersBySeriesIdUseCase,
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getSeriesReviews: GetSeriesReviewsUseCase,
    private val isLoggedInByAccountUseCase: IsLoggedInByAccountUseCase,
    private val addRatingUseCase: AddSeriesRatingUseCase,
    private val getUserRatingUseCase: GetUserSeriesRatingUseCase,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SeriesDetailsScreenState, SeriesDetailsEffect>(
    SeriesDetailsScreenState(
        seriesUi = SeriesUi(
            id = savedStateHandle.toRoute<SeriesDetailsRoute>().seriesID
        )
    )
), SeriesDetailsInteractionListener {


    init {
        observeContentPreference()
        loadSeriesDetailsScreen(state.value.seriesUi.id)
    }


    private fun loadSeriesDetailsScreen(seriesID: Int = state.value.seriesUi.id) {
        updateState {
            it.copy(
                isLoading = true,
                isNoInternet = false,
            )
        }

        loadSeriesDetails(seriesID)
        loadSeason(seriesID)
        loadRecommendedSeries(seriesID)
        loadSeriesReviews(seriesID)
        loadSeriesPeople(seriesID)
    }

    override fun onGiveStarsCardClick() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = true
            )
        }
    }

    override fun onDismissAddToCollectionBottomSheet() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = false
            )
        }
    }


    override fun onDismissGiveStarsBottomSheet() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = false
            )
        }
    }

    override fun onDismissLoginBottomSheet() {
        updateState {
            it.copy(
                isVisibleLoginBottomSheet = false
            )
        }
    }

    override fun onCastCardClick(personId: Int) {
        sendEffect(
            SeriesDetailsEffect.NavigateToCastDetails(
                personId = personId
            )
        )
    }

    override fun onShowMoreSeasonsTextClick(seriesId: Int) {
        sendEffect(
            SeriesDetailsEffect.NavigateToSeasons(
                seriesId = seriesId
            )
        )
    }

    override fun onShowMoreRecommendedSeriesTextClick(seriesId: Int, title: String) {
        sendEffect(
            SeriesDetailsEffect.NavigateToRecommendedSeries(
                seriesId = seriesId,
                title = title
            )
        )
    }

    override fun onSeriesPosterClick(seriesId: Int) {
        sendEffect(
            SeriesDetailsEffect.NavigateToSeriesDetails(seriesId)
        )
    }

    override fun onAddRateButtonClick() {
        executeIfLoggedIn(
            block = {
                updateState { it.copy(isVisibleGiveStarsBottomSheet = false) }

                safeExecute(
                    onSuccess = { loadSeriesDetails(state.value.seriesUi.id) },
                    onError = ::onError
                ) {
                    addRatingUseCase(
                        seriesId = state.value.seriesUi.id,
                        rating = state.value.currentRating.toFloat()
                    )
                }
            },
            ifNotLoggedIn = {
                updateState {
                    it.copy(
                        isVisibleLoginBottomSheet = true
                    )
                }
            }
        )
    }

    override fun onRateChange(rate: Int) {
        updateState { it.copy(currentRating = rate) }
    }

    override fun onShowMoreReviewsTextClick(seriesId: Int) {
        sendEffect(
            SeriesDetailsEffect.NavigateToReviews(seriesId)
        )
    }

    override fun onPlayButtonClick(url: String) {
        sendEffect(SeriesDetailsEffect.NavigateToYouTubePlayer(url))
    }

    override fun onLoginButtonClick() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = false,
                isVisibleLoginBottomSheet = false
            )
        }
        sendEffect(SeriesDetailsEffect.NavigateToLogIn)
    }

    override fun onBackClick() {
        sendEffect(SeriesDetailsEffect.OnBackButtonClick)
    }

    override fun onRetryClick() {
        loadSeriesDetailsScreen(state.value.seriesUi.id)
    }

    private fun loadSeriesDetails(seriesId: Int) {
        updateState { it.copy(isLoading = true) }
        executeIfLoggedIn(
            block = {
                safeExecute(
                    onSuccess = ::loadSeriesDetailsSuccess,
                    onError = ::onError
                ) {
                    val userRating = getUserRatingUseCase(seriesId)
                    val series = getSeriesDetails(seriesId)
                    series.copy(
                        userRating = userRating
                    )
                }
            },
            ifNotLoggedIn = {
                safeExecute(
                    onSuccess = ::loadSeriesDetailsSuccess,
                ) {
                    getSeriesDetails(seriesId)
                }
            }
        )

    }


    private fun loadSeriesDetailsSuccess(series: Series) {
        updateState {
            it.copy(
                seriesUi = series.toUi(),
                isLoading = false,
                isNoInternet = false
            )
        }
        loadSeriesGenres(series.genreIDs)
    }

    private fun loadSeriesGenres(genreIDs: List<Int>) {
        safeExecute(
            onSuccess = ::loadSeriesGenresSuccess,
            onError = ::onError
        ) {
            getSeriesGenres(genreIDs)
        }
    }

    private fun loadSeriesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                genres = genres.map { genre -> genre.title },
                isLoading = false
            )
        }
    }


    private fun loadSeason(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeasonsSuccess,
            onError = ::onError
        ) {
            getLastSeasons(seriesId)
        }
    }

    private fun loadSeasonsSuccess(season: List<Season>) {
        updateState {
            it.copy(
                seasons = season.map { season -> season.toUi() },
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun loadSeriesPeople(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesPeopleSuccess,
            onError = ::loadSeriesPeopleError
        ) {
            getCastAndCrewOfSeries(seriesId)
        }
    }

    private fun loadSeriesPeopleError(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(SeriesDetailsEffect.Error(error))
    }

    private fun loadSeriesPeopleSuccess(mediaMembers: MediaMemberRepository.MediaMembers) {
        val cast = mediaMembers.cast.take(10)
        val crew = mediaMembers.crew.take(10)
        val mappedCrew = crew.map { it.toCrewUi() }
        val mappedCast = cast.map { it.toCastUi() }

        updateState {
            it.copy(
                cast = mappedCast,
                crew = mappedCrew.groupByRole(),
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun loadRecommendedSeries(seriesId: Int, page: Int = 1) {
        safeExecute(
            onSuccess = ::loadRecommendedSeriesSuccess,
            onError = ::onError
        ) {
            getRecommendedSeries(seriesId = seriesId, page = page)
        }
    }

    private fun loadRecommendedSeriesSuccess(recommendedSeries: List<Series>) {
        updateState {
            it.copy(
                recommendedSeries = recommendedSeries.map { poster ->
                    Poster(
                        id = poster.id,
                        name = poster.name,
                        imageUrl = poster.posterUrl,
                        rating = poster.rating
                    )
                },
                isLoading = false,
                isNoInternet = false
            )
        }
    }


    private fun loadSeriesReviews(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesReviewsSuccess,
            onError = ::onError
        ) {
            getSeriesReviews(seriesId, 1)
        }
    }

    private fun loadSeriesReviewsSuccess(reviews: List<Review>) {
        updateState {
            it.copy(
                seriesReviews = reviews.map { review -> review.toUi() },
                isLoading = false,
                isNoInternet = false
            )
        }
    }


    private fun executeIfLoggedIn(
        block: () -> Unit,
        ifNotLoggedIn: () -> Unit
    ) {
        safeExecute(
            onSuccess = { isLoggedIn ->
                updateState {
                    it.copy(
                        isLoading = false,
                        isNoInternet = false
                    )
                }

                if (isLoggedIn) {
                    block()
                } else {
                    ifNotLoggedIn()
                }
            },
            onError = ::onError,
            block = isLoggedInByAccountUseCase::invoke
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
    private fun onError(throwable: Throwable) {
        val isNetworkError =
            throwable is NoInternetException || throwable is UserNoInternetException

        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = isNetworkError || it.isNoInternet
            )
        }

        sendEffect(SeriesDetailsEffect.Error(throwable))
    }
}