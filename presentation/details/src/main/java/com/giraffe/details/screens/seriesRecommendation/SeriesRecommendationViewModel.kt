package com.giraffe.details.screens.seriesRecommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.giraffe.details.base.BasePagingSource
import com.giraffe.details.models.SeriesUi
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SeriesRecommendationViewModel(
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val seriesId: Long
) : ViewModel() {
    val recommendationScreenState = Pager(config = PagingConfig(20)) {
        BasePagingSource { page -> getRecommendedSeries(seriesId, page) }
    }
        .flow
        .map { it.map(SeriesUi::fromEntity) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}