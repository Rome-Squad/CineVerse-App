package com.giraffe.details.screens.seriesdetails

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.SeasonUi
import com.giraffe.details.models.SeriesUi
import com.giraffe.details.models.groupByRole
import com.giraffe.details.models.toCastUi
import com.giraffe.details.models.toCrewUi
import com.giraffe.details.models.toReviewUI
import com.giraffe.media.entity.Review
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.usecase.GetPeopleBySeriesIdUseCase
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetLastSeasonsUseCase
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase

class SeriesDetailsViewModel(
    private val getSeriesDetails: GetSeriesDetailsUseCase,
    private val getLastSeasons: GetLastSeasonsUseCase,
    private val getSeriesGenres: GetSeriesGenresUseCase,
    private val getCastOfSeries: GetPeopleBySeriesIdUseCase,
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getSeriesReviews: GetSeriesReviewsUseCase,
) :
    BaseViewModel<SeriesDetailsScreenState, SeriesDetailsEffect>(
        SeriesDetailsScreenState()
    ), SeriesDetailsInteractionListener {

//    init {
//        loadSeries(2288)
//        loadSeason(2288)
//        loadSeriesPeople(2288)
//        loadRecommendedSeries(2288, 5)
//        loadSeriesReviews(2288)
//    }

    fun loadSeries(seriesId: Int) {
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
    }

    fun loadSeriesDetailsError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingSeries = false,
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


    private fun loadSeriesPeople(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadMoviePeopleSuccess,
            onError = ::loadMoviePeopleError
        ) {
            getCastOfSeries(seriesId)
        }
    }

    private fun loadMoviePeopleSuccess(people: List<Person>) {
        val cast = people.filter { it.type == PersonType.CAST }.take(10)
        val crew = people.filter { it.type == PersonType.CREW }
        val mappedCrew = crew.map { it.toCrewUi() }
        updateState {
            it.copy(
                isLoadingPeople = false,
                cast = cast.map { it.toCastUi() },
                crew = mappedCrew.groupByRole()
            )
        }
    }

    private fun loadMoviePeopleError(error: Throwable) {
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
        TODO("Not yet implemented")
    }

    override fun showMoreCast() {
        TODO("Not yet implemented")
    }

    override fun showMoreCrew() {
        TODO("Not yet implemented")
    }

    override fun showMoreRecommendedSeries() {
        TODO("Not yet implemented")
    }

    override fun onClickGiveStars() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = true
            )
        }
    }

    override fun showMoreReviews() {
        TODO("Not yet implemented")
    }

    override fun onClickMovie(movieId: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickAddToCollection() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = true
            )
        }
    }

    override fun onClickCreateCollection() {
        TODO("Not yet implemented")
    }

    override fun onDismissAddToCollectionBottomSheet() {
        updateState {
            it.copy(
                isVisibleAddToCollectionBottomSheet = false
            )
        }
    }

    override fun onDismissLoginBottomSheet() {
        TODO("Not yet implemented")
    }

    override fun onDismissGiveStarsBottomSheet() {
        updateState {
            it.copy(
                isVisibleGiveStarsBottomSheet = false
            )
        }
    }
}