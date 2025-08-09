package com.giraffe.presentation.details.screens.recommended.series

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.media.entity.Genre
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import com.giraffe.presentation.details.base.BasePagingSource
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.navigation.routes.RecommendedSeriesRoute
import com.giraffe.presentation.details.utils.toSeriesUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecommendedSeriesViewModel @Inject constructor(
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<RecommendedSeriesScreenState, RecommendedSeriesEffect>(
    RecommendedSeriesScreenState()
),
    RecommendedInteractionListener {

    init {
        val seriesId: Int = savedStateHandle.toRoute<RecommendedSeriesRoute>().seriesID
        val title: String = savedStateHandle.toRoute<RecommendedSeriesRoute>().titleSeries

        updateState {
            it.copy(
                seriesId = seriesId,
                seriesTitle = title
            )
        }

        getSeriesGenres()

        state.value.seriesId?.let {
            getRecommendedSeries(it)
        }
    }

    private fun getSeriesGenres() {
        safeExecute(
            onSuccess = ::onGetSeriesGenresSuccess,
            onError = ::onGetSeriesGenresFailure
        ) {
            getSeriesGenresUseCase()
        }
    }

    private fun onGetSeriesGenresSuccess(genres: List<Genre>) {
        updateState {
            it.copy(
                isLoading = false,
                seriesGenres = genres
            )
        }
    }

    private fun onGetSeriesGenresFailure(error: Throwable) {
        updateState {
            it.copy(
                isLoading = false
            )
        }

        sendEffect(RecommendedSeriesEffect.Error(error))
    }


    private fun getRecommendedSeries(
        movieId: Int
    ) {

        safeExecute(
            onSuccess = ::onGetRecommendedSeriesSuccess,
            onError = ::onGetRecommendedSeriesError
        ) {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = 15,
                    prefetchDistance = 5,
                    initialLoadSize = 15
                )
            ) {
                BasePagingSource { page ->
                    getRecommendedSeries(movieId, page)
                }
            }

            pager
                .flow
                .cachedIn(viewModelScope)
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    PagingData.empty()
                )
        }
    }

    private fun onGetRecommendedSeriesSuccess(seriesFlow: Flow<PagingData<Series>>) {
        val seriesUiFlow = seriesFlow.map { pagingData ->
            pagingData.map { it.toSeriesUi(state.value.seriesGenres) }
        }

        updateState {
            it.copy(
                recommendedSeriesFlow = seriesUiFlow,
                isLoading = false
            )
        }
    }

    private fun onGetRecommendedSeriesError(error: Throwable) {
        sendEffect(RecommendedSeriesEffect.Error(error))
    }

    override fun navigateToSeriesDetailsScreen(seriesId: Int) {
        sendEffect(RecommendedSeriesEffect.NavigateToSeriesDetails(seriesId))
    }
}