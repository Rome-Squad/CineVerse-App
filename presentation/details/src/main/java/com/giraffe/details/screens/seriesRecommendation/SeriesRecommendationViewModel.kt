package com.giraffe.details.screens.seriesRecommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.giraffe.details.base.BasePagingSource
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SeriesRecommendationViewModel(
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val seriesId: Long
) : ViewModel() {
    val recommendationScreenState = Pager(config = PagingConfig(20)) {
        BasePagingSource { page -> getRecommendedSeries(seriesId, page) }
    }
        .flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}