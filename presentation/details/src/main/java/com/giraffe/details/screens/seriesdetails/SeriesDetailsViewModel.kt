package com.giraffe.details.screens.seriesdetails

import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.SeasonUi
import com.giraffe.details.models.SeriesUi
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
    }

    fun loadSeries(seriesId: Int) {
        safeExecute(
            onSuccess = {
                loadSeriesDetailsSuccess(it)
            },
            onError = {
                loadSeriesDetailsError(it)
            }
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
    }


    fun loadSeason(seriesId: Int) {
        safeExecute(
            onSuccess = {
                loadLastSeasonsSuccess(it)
            },
            onError = {
                loadLastSeasonsError(it)
            }
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
    }


    fun loadGenres() {
        safeExecute(
            onSuccess = {
                loadGenresSuccess(it)
            },
            onError = {
                loadGenresError(it)
            }
        ) {
            getSeriesGenres()
        }
    }

    fun loadGenresSuccess(seriesGenre: List<SeriesGenre>) {
        updateState {
            it.copy(
                genres = seriesGenre.map { it.name },
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
    }


}