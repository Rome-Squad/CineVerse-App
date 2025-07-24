package com.giraffe.details.screens.seriesdetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.SeasonUi
import com.giraffe.details.models.SeriesUi
import com.giraffe.details.models.groupByRole
import com.giraffe.details.models.toCastUi
import com.giraffe.details.models.toCrewUi
import com.giraffe.details.models.toReviewUI
import com.giraffe.details.screens.seriesdetails.screen.SeriesDetailsRoute
import com.giraffe.media.entity.Genre
import com.giraffe.media.entity.Review
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.usecase.GetPeopleBySeriesIdUseCase
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetLastSeasonsUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase

class SeriesDetailsViewModel(
    private val getSeriesDetails: GetSeriesDetailsUseCase,
    private val getLastSeasons: GetLastSeasonsUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    private val getCastAndCrewOfSeries: GetPeopleBySeriesIdUseCase,
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getSeriesReviews: GetSeriesReviewsUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<SeriesDetailsScreenState, SeriesDetailsEffect>(
        SeriesDetailsScreenState()
    ), SeriesDetailsInteractionListener {

    val seriesID = savedStateHandle.toRoute<SeriesDetailsRoute>().seriesID

    init {
        loadSeriesDetails(seriesID)
        loadSeason(seriesID)
        loadRecommendedSeries(seriesID, 1)
        loadSeriesReviews(seriesID)
        loadSeriesPeople(seriesID)
    }

    fun loadError(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
            )
        }
        sendEffect(SeriesDetailsEffect.Error(error))
    }

    fun loadSeriesDetails(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesDetailsSuccess,
            onError = ::loadError
        ) {
            updateState {
                it.copy(
                    isLoading = true
                )
            }
            getSeriesDetails(seriesId)
        }
    }

    fun loadSeriesDetailsSuccess(series: Series) {
        updateState {
            it.copy(
                seriesDetails = SeriesUi.fromEntity(series),
                isLoading = false
            )
        }
        loadSeriesGenres(series.genreIDs)
    }


    fun loadSeriesGenres(genreIDs: List<Int>) {
        safeExecute(
            onSuccess = ::loadSeriesGenresSuccess,
            onError = ::loadError
        ) {
            getSeriesGenres(genreIDs)
        }
    }

    fun loadSeriesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                genres = genres.map { it.title },
            )
        }
    }


    fun loadSeason(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeasonsSuccess,
            onError = ::loadError
        ) {
            getLastSeasons(seriesId)
        }
    }

    fun loadSeasonsSuccess(season: List<Season>) {
        updateState {
            it.copy(
                seasons = season.map { SeasonUi.fromEntity(it) },
                isLoading = false
            )
        }
    }


    fun loadSeriesPeople(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesPeopleSuccess,
            onError = ::loadError
        ) {
            getCastAndCrewOfSeries(seriesId)
        }
    }

    fun loadSeriesPeopleSuccess(people: List<Person>) {
        val cast = people.filter { it.type == PersonType.CAST }.take(10)
        val crew = people.filter { it.type == PersonType.CREW }.take(10)
        val mappedCrew = crew.map { it.toCrewUi() }
        updateState {
            it.copy(
                cast = cast.map { it.toCastUi() },
                crew = mappedCrew.groupByRole()
            )
        }
    }


    fun loadRecommendedSeries(seriesId: Int, page: Int) {
        safeExecute(
            onSuccess = ::loadRecommendedSeriesSuccess,
            onError = ::loadError
        ) {
            getRecommendedSeries(seriesId = seriesId.toLong(), page = page)
        }
    }

    fun loadRecommendedSeriesSuccess(recommendedSeries: List<Series>) {
        updateState {
            it.copy(
                recommendedSeries = recommendedSeries.map {
                    Poster(
                        id = it.id,
                        name = it.name,
                        imageUri = it.posterUrl,
                        rating = it.rating
                    )
                },
            )
        }
    }


    fun loadSeriesReviews(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesReviewsSuccess,
            onError = ::loadError
        ) {
            getSeriesReviews(seriesId)
        }
    }

    fun loadSeriesReviewsSuccess(reviews: List<Review>) {
        updateState {
            it.copy(
                seriesReviews = reviews.map { it.toReviewUI() },
            )
        }
    }


    override fun onClickGiveStars() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = true
            )
        }
    }


    override fun onClickAddToCollection() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = true
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

    override fun navigateToCastDetailsScreen(personId: Int) {
        sendEffect(
            SeriesDetailsEffect.NavigateToCastDetails(
                personId = personId
            )
        )
    }

    override fun navigateToSeasonsScreen(seriesId: Int) {
        sendEffect(
            SeriesDetailsEffect.NavigateToSeasons(
                seriesId = seriesId
            )
        )
    }

    override fun navigateToRecommendedSeriesScreen(seriesId: Int, title: String) {
        sendEffect(
            SeriesDetailsEffect.NavigateToRecommendedSeries(
                seriesId = seriesId,
                title = title
            )
        )
    }

    override fun navigateToSeriesDetails(seriesId: Int) {
        sendEffect(
            SeriesDetailsEffect.NavigateToSeriesDetails(seriesId)
        )
    }
}