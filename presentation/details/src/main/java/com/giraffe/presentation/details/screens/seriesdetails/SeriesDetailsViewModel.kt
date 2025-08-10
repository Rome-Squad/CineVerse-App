package com.giraffe.presentation.details.screens.seriesdetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.mediaMember.repository.MediaMemberRepository
import com.giraffe.media.mediaMember.usecase.GetPeopleBySeriesIdUseCase
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.AddRecentSeriesUseCase
import com.giraffe.media.series.usecase.AddSeriesRatingUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeasonsUseCase
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.model.SeasonUi
import com.giraffe.presentation.details.model.SeriesUi
import com.giraffe.presentation.details.model.groupByRole
import com.giraffe.presentation.details.model.toCastUi
import com.giraffe.presentation.details.model.toCrewUi
import com.giraffe.presentation.details.model.toReviewUI
import com.giraffe.presentation.details.screens.seriesdetails.screen.SeriesDetailsRoute
import com.giraffe.user.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val getSeriesDetails: GetSeriesDetailsUseCase,
    private val getLastSeasons: GetSeasonsUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    private val getCastAndCrewOfSeries: GetPeopleBySeriesIdUseCase,
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getSeriesReviews: GetSeriesReviewsUseCase,
    private val storeRecentSeriesUseCase: AddRecentSeriesUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val addRatingUseCase: AddSeriesRatingUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SeriesDetailsScreenState, SeriesDetailsEffect>(
    SeriesDetailsScreenState()
), SeriesDetailsInteractionListener {

    private val seriesID = savedStateHandle.toRoute<SeriesDetailsRoute>().seriesID

    init {
        loadSeriesDetailsScreen()
    }

    fun loadSeriesDetailsScreen() {
        updateState {
            it.copy(
                isLoading = true,
                isNetworkError = false,
                errorMessage = null
            )
        }
        loadSeriesDetails(seriesID)
        loadSeason(seriesID)
        loadRecommendedSeries(seriesID, 1)
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
        safeExecute {
            if (isLoggedInUseCase()) {
                updateState { it.copy(isVisibleGiveStarsBottomSheet = false) }
                addRatingUseCase(
                    seriesId = seriesID,
                    rating = state.value.currentRating.toFloat()
                )
            } else {
                updateState { it.copy(isVisibleLoginBottomSheet = true) }
            }
        }
    }

    override fun onRateChange(rate: Int) {
        safeExecute {
            updateState { it.copy(currentRating = rate) }
        }
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

    override fun onBackButtonClick() {
        sendEffect(SeriesDetailsEffect.OnBackButtonClick)
    }

    private fun loadSeriesDetails(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesDetailsSuccess,
            onError = ::loadSeriesDetailsError
        ) {
            updateState { it.copy(isLoading = true) }
            getSeriesDetails(seriesId)
        }
    }


    private fun loadSeriesDetailsSuccess(series: Series) {
        updateState {
            it.copy(
                seriesDetails = SeriesUi.Companion.fromEntity(series),
                isLoading = false
            )
        }
        loadSeriesGenres(series.genreIDs)
        saveSeriesToRecent(series)
    }

    private fun loadSeriesDetailsError(errorMsgRes: Int, isNetworkError: Boolean) {
        updateState {
            it.copy(
                errorMessage = errorMsgRes,
                isNetworkError = isNetworkError,
                isLoading = false
            )
        }
    }

    private fun saveSeriesToRecent(series: Series) {
        safeExecute {
            storeRecentSeriesUseCase(series)
        }
    }


    private fun loadSeriesGenres(genreIDs: List<Int>) {
        safeExecute(
            onSuccess = ::loadSeriesGenresSuccess,
            onError = ::loadSeriesGenresError
        ) {
            getSeriesGenres(genreIDs)
        }
    }

    private fun loadSeriesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                genres = genres.map { it.title },
            )
        }
    }

    private fun loadSeriesGenresError(errorMsgRes: Int, isNetworkError: Boolean) {
        updateState {
            it.copy(
                errorMessage = errorMsgRes,
                isNetworkError = isNetworkError,
                isLoading = false
            )
        }
    }


    private fun loadSeason(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeasonsSuccess,
            onError = ::loadSeasonsError
        ) {
            getLastSeasons(seriesId)
        }
    }

    private fun loadSeasonsSuccess(season: List<Season>) {
        updateState {
            it.copy(
                seasons = season.map { SeasonUi.Companion.fromEntity(it) },
                isLoading = false
            )
        }
    }

    private fun loadSeasonsError(errorMsgRes: Int, isNetworkError: Boolean) {
        updateState {
            it.copy(
                errorMessage = errorMsgRes,
                isNetworkError = isNetworkError,
                isLoading = false
            )
        }
    }


    private fun loadSeriesPeople(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesPeopleSuccess,
            onError = {}
        ) {
            getCastAndCrewOfSeries(seriesId)
        }
    }

    private fun loadSeriesPeopleSuccess(mediaMembers: MediaMemberRepository.MediaMembers) {
        val cast = mediaMembers.cast.take(10)
        val crew = mediaMembers.crew.take(10)
        val mappedCrew = crew.map { it.toCrewUi() }
        val mappedCast = cast.map { it.toCastUi() }


        updateState {
            it.copy(
                cast = mappedCast,
                crew = mappedCrew.groupByRole()
            )
        }
    }


    private fun loadRecommendedSeries(seriesId: Int, page: Int) {
        safeExecute(
            onSuccess = ::loadRecommendedSeriesSuccess,
            onError = {}
        ) {
            getRecommendedSeries(seriesId = seriesId, page = page)
        }
    }

    private fun loadRecommendedSeriesSuccess(recommendedSeries: List<Series>) {
        updateState {
            it.copy(
                recommendedSeries = recommendedSeries.map {
                    Poster(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.posterUrl,
                        rating = it.rating
                    )
                },
            )
        }
    }


    private fun loadSeriesReviews(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesReviewsSuccess,
            onError = {}
        ) {
            getSeriesReviews(seriesId, 1)
        }
    }

    private fun loadSeriesReviewsSuccess(reviews: List<Review>) {
        updateState {
            it.copy(
                seriesReviews = reviews.map { it.toReviewUI() }
            )
        }
    }
}