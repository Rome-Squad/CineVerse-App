package com.giraffe.details.screens.seriesReview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.giraffe.details.base.BasePagingSource
import com.giraffe.details.models.toReviewUI
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SeriesReviewViewModel(
    private val getSeriesReviews: GetSeriesReviewsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val seriesId: Int = savedStateHandle["id"] ?: 0

    val reviewsFlow = Pager(config = PagingConfig(pageSize = 20), initialKey = 1) {
        BasePagingSource { page -> getSeriesReviews(seriesId, page) }
    }
        .flow
        .map { it.map { it.toReviewUI() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}