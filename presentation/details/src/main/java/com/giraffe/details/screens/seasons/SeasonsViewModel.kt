package com.giraffe.details.screens.seasons

import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.SeasonUi
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.usecase.GetLastSeasonsUseCase

class SeasonsViewModel(
    private val getSeasons: GetLastSeasonsUseCase
) : BaseViewModel<SeasonsScreenState, SeasonsEffect>(
    SeasonsScreenState()
) {

    fun loadSeason(seriesId: Int) {
        safeExecute(
            onSuccess = {
                loadSeasonsSuccess(it)
            },
            onError = {
                loadSeasonsError(it)
            }
        ) {
            getSeasons(seriesId)
        }
    }

    fun loadSeasonsSuccess(season: List<Season>) {
        updateState {
            it.copy(
                seasons = season.map { SeasonUi.fromEntity(it) },
                isLoadingSeason = false
            )
        }
    }

    fun loadSeasonsError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingSeason = false,
            )
        }
    }
}