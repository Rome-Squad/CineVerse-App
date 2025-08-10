package com.giraffe.presentation.details.screens.seasons

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.media.series.entity.Season
import com.giraffe.media.series.usecase.GetSeasonsUseCase
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.SeasonsRoute
import com.giraffe.presentation.details.utils.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonsViewModel @Inject constructor(
    private val getSeasons: GetSeasonsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SeasonsScreenState, SeasonsEffect>(
    SeasonsScreenState(
        seriesId = savedStateHandle.toRoute<SeasonsRoute>().seriesID
    )
) {

    init {
        state.value.seriesId?.let {
            loadSeason(it)
        }
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
                seasons = season.map(Season::toUi),
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