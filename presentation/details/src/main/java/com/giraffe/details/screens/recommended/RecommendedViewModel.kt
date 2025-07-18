package com.giraffe.details.screens.recommended

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.details.base.BaseViewModel
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase

class RecommendedViewModel(
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
) :
    BaseViewModel<RecommendedScreenState, RecommendedEffect>(
        RecommendedScreenState()
    ), RecommendedInteractionListener {


    fun loadRecommendedSeries(seriesId: Int, page: Int) {
        safeExecute(
            onSuccess = ::loadRecommendedSeriesSuccess,
            onError = ::loadRecommendedError
        ) {
            getRecommendedSeries(seriesId = seriesId.toLong(), page = page)
        }
    }

    fun loadRecommendedSeriesSuccess(recommendedSeries: List<Series>) {
        updateState {
            it.copy(
                recommended = recommendedSeries.map {
                    Poster(
                        id = it.id,
                        name = it.name,
                        imageUri = it.posterUrl,
                        rating = it.rating
                    )
                },
                isLoadingRecommended = false
            )
        }
    }

    fun loadRecommendedError(error: Throwable) {
        updateState {
            it.copy(
                isLoadingRecommended = false,
            )
        }
        sendEffect(RecommendedEffect.Error(error))
    }
}