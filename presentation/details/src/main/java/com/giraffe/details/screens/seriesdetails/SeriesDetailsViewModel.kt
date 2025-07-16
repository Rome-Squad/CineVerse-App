package com.giraffe.details.screens.seriesdetails

import com.giraffe.details.base.BaseViewModel
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase

class SeriesDetailsViewModel(private val getSeriesDetails: GetSeriesDetailsUseCase) :
    BaseViewModel<SeriesDetailsScreenState, SeriesDetailsEffect>(
        SeriesDetailsScreenState()
    ) {

    init {
        loadSeries(2)
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
}