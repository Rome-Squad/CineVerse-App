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
import com.giraffe.media.exception.NoInternetException as MediaNoInternetException
import com.giraffe.user.exception.NoInternetException as UserInternetException

@HiltViewModel
class SeasonsViewModel @Inject constructor(
    private val getSeasons: GetSeasonsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SeasonsScreenState, SeasonsEffect>(
    SeasonsScreenState(
        seriesId = savedStateHandle.toRoute<SeasonsRoute>().seriesID
    )
), SeasonInteractionListener {

    init {
        state.value.seriesId?.let {
            loadSeason(it)
        }
    }

    private fun loadSeason(seriesId: Int) {
        updateState { it.copy(isLoading = true) }

        safeExecute(
            onSuccess = ::loadSeasonsSuccess,
            onError = ::loadSeasonsError
        ) {
            getSeasons(seriesId)
        }
    }

    private fun loadSeasonsSuccess(seasons: List<Season>) {
        updateState {
            it.copy(
                seasons = seasons.map(Season::toUi),
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun loadSeasonsError(error: Throwable) {
        val isNoInternetError = error is UserInternetException || error is MediaNoInternetException

        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = isNoInternetError
            )
        }
        sendEffect(SeasonsEffect.ShowError(error))
    }

    override fun onBackClick() {
        sendEffect(SeasonsEffect.NavigateBack)
    }

    override fun retry() {
        state.value.seriesId?.let {
            loadSeason(it)
        }
    }
}