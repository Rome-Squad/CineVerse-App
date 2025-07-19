package com.giraffe.details.screens.seriesdetails

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.SeasonUi
import com.giraffe.details.models.SeriesUi
import com.giraffe.details.models.groupByRole
import com.giraffe.details.models.toCastUi
import com.giraffe.details.models.toCrewUi
import com.giraffe.details.models.toReviewUI
import com.giraffe.details.screens.castDetails.CastDetailsEffect
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
    seriesID: Int,
    private val getSeriesDetails: GetSeriesDetailsUseCase,
    private val getLastSeasons: GetLastSeasonsUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    private val getCastAndCrewOfSeries: GetPeopleBySeriesIdUseCase,
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getSeriesReviews: GetSeriesReviewsUseCase,
) :
    BaseViewModel<SeriesDetailsScreenState, SeriesDetailsEffect>(
        SeriesDetailsScreenState()
    ), SeriesDetailsInteractionListener {


    init {
        loadSeriesDetails(seriesID)
        loadSeason(seriesID)
        loadRecommendedSeries(seriesID, 1)
        loadSeriesReviews(seriesID)
        loadSeriesPeople(seriesID)
    }

    fun loadSeriesDetails(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesDetailsSuccess,
            onError = ::loadSeriesDetailsError
        ) {
            getSeriesDetails(seriesId)
        }
    }

    fun loadSeriesDetailsSuccess(series: Series) {
        updateState {
            it.copy(
                seriesDetails = SeriesUi.fromEntity(series),
                isLoadingSeries = false
            )
        }
        loadSeriesGenres(series.genreIDs)
    }

    fun loadSeriesDetailsError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingSeries = false,
            )
        }
        sendEffect(SeriesDetailsEffect.Error(error))
    }


    fun loadSeriesGenres(genreIDs: List<Int>) {

        safeExecute(
            onSuccess = ::loadSeriesGenresSuccess,
            onError = ::loadSeriesGenresError
        ) {
            getSeriesGenres(genreIDs)
        }
    }

    fun loadSeriesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                genres = genres.map { it.title },
                isLoadingGenres = false
            )
        }
    }

    fun loadSeriesGenresError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingGenres = false,
            )
        }
        sendEffect(SeriesDetailsEffect.Error(error))
    }


    fun loadSeason(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadLastSeasonsSuccess,
            onError = ::loadLastSeasonsError
        ) {
            getLastSeasons(seriesId)
        }
    }

    fun loadLastSeasonsSuccess(season: List<Season>) {
        updateState {
            it.copy(
                seasons = season.map { SeasonUi.fromEntity(it) },
                isLoadingSeason = false
            )
        }
    }

    fun loadLastSeasonsError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingSeason = false,
            )
        }
        sendEffect(SeriesDetailsEffect.Error(error))
    }


    fun loadSeriesPeople(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesPeopleSuccess,
            onError = ::loadSeriesPeopleError
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
                isLoadingPeople = false,
                cast = cast.map { it.toCastUi() },
                crew = mappedCrew.groupByRole()
            )
        }
    }

    fun loadSeriesPeopleError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingPeople = false,
            )
        }
        sendEffect(SeriesDetailsEffect.Error(error))
    }


    fun loadRecommendedSeries(seriesId: Int, page: Int) {
        safeExecute(
            onSuccess = ::loadRecommendedSeriesSuccess,
            onError = ::loadRecommendedSeriesError
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
                isLoadingSeries = false
            )
        }
    }

    fun loadRecommendedSeriesError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingSeries = false,
            )
        }
        sendEffect(SeriesDetailsEffect.Error(error))
    }


    fun loadSeriesReviews(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeriesReviewsSuccess,
            onError = ::loadSeriesReviewsError
        ) {
            getSeriesReviews(seriesId)
        }
    }

    fun loadSeriesReviewsSuccess(reviews: List<Review>) {
        updateState {
            it.copy(
                seriesReviews = reviews.map { it.toReviewUI() },
                isLoadingSeries = false
            )
        }
    }

    fun loadSeriesReviewsError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingSeries = false,
            )
        }
        sendEffect(SeriesDetailsEffect.Error(error))
    }


    override fun showMoreSeason() {
    }

    override fun showMoreCast() {
    }


    override fun showMoreRecommendedSeries() {
    }

    override fun onClickGiveStars() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = true
            )
        }
    }

    override fun showMoreReviews() {
    }

    override fun onClickMovie(movieId: Int) {
    }

    override fun onClickAddToCollection() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = true
            )
        }
    }

    override fun onClickCreateCollection() {
    }

    override fun onDismissAddToCollectionBottomSheet() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = false
            )
        }
    }

    override fun onDismissLoginBottomSheet() {
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

    override fun navigateToSeriesDetailsScreen(seriesId: Int) {
        sendEffect(
            SeriesDetailsEffect.NavigateToSeriesDetails(
                seriesId = seriesId
            )
        )
    }
}