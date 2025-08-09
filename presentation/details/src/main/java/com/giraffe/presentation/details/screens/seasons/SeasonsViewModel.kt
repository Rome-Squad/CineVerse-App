package com.giraffe.presentation.details.screens.seasons

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.usecase.GetLastSeasonsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.SeasonsRoute
import com.giraffe.presentation.details.utils.toSeasonUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonsViewModel @Inject constructor(
    private val getSeasons: GetLastSeasonsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SeasonsScreenState, SeasonsEffect>(
    SeasonsScreenState()
) {



    init {
        val seriesID = savedStateHandle.toRoute<SeasonsRoute>().seriesID
        updateState {
            it.copy(
                seriesId = seriesID,
                isLoading = true,
            )
        }

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
                seasons = season.map { season -> season.toSeasonUi() },
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