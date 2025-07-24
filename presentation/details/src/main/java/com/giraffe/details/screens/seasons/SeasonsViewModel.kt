package com.giraffe.details.screens.seasons

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.SeasonUi
import com.giraffe.details.screens.seasons.screen.SeasonsRoute
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.usecase.GetLastSeasonsUseCase

class SeasonsViewModel(
    private val getSeasons: GetLastSeasonsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SeasonsScreenState, SeasonsEffect>(
    SeasonsScreenState()
) {

    val seriesID = savedStateHandle.toRoute<SeasonsRoute>().seriesID

    init {
        loadSeason(seriesID)
    }

    fun loadSeason(seriesId: Int) {
        safeExecute(
            onSuccess = ::loadSeasonsSuccess,
            onError = ::loadSeasonsError
        ) {
            getSeasons(seriesId)
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

    fun loadSeasonsError(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
            )
        }
        sendEffect(SeasonsEffect.Error(error))
    }
}