package com.giraffe.details.screens.seriesdetails

import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.SeasonUi
import com.giraffe.details.models.SeriesUi
import com.giraffe.details.models.groupByRole
import com.giraffe.details.models.toCastUi
import com.giraffe.details.models.toCrewUi
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.entity.PersonType
import com.giraffe.media.person.usecase.GetPeopleBySeriesIdUseCase
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.entity.SeriesGenre
import com.giraffe.media.series.usecase.GetLastSeasonsUseCase
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase

class SeriesDetailsViewModel(
    private val getSeriesDetails: GetSeriesDetailsUseCase,
    private val getLastSeasons: GetLastSeasonsUseCase,
    private val getSeriesGenres: GetSeriesGenresUseCase,
    private val getCastOfSeries: GetPeopleBySeriesIdUseCase,
    private val getSeriesReviews: GetSeriesReviewsUseCase,
) :
    BaseViewModel<SeriesDetailsScreenState, SeriesDetailsEffect>(
        SeriesDetailsScreenState()
    ) {

    init {
        loadSeries(2288)
        loadSeason(2288)
        loadGenres()
        loadSeriesPeople(2288)
    }

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


    fun loadGenres() {
        safeExecute(
            onSuccess = ::loadGenresSuccess,
            onError = ::loadGenresError
        ) {
            getSeriesGenres()
        }
    }
    fun loadGenresSuccess(seriesGenre: List<SeriesGenre>) {
        updateState {
            it.copy(
                //genres = seriesGenre.map { state.value.seriesDetails.genreIDs.contains(it.id) },
                isLoadingGenres = false
            )
        }
    }
    fun loadGenresError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingGenres = false,
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
        val crew = people.filter { it.type == PersonType.CREW}
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
}