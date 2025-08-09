package com.giraffe.presentation.details.screens.reviewScreen

import androidx.paging.PagingData
import com.giraffe.presentation.details.models.ReviewUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ReviewsScreenState(
    val movieId: Int? = null,
    val seriesId: Int? = null,
    val reviewsFlow: Flow<PagingData<ReviewUI>> = flowOf(),
    val isLoading: Boolean = false
)
