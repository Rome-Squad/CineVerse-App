package com.giraffe.details.screens.reviewScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.details.base.BasePagingSource
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.models.toReviewUI
import com.giraffe.media.entity.Review
import com.giraffe.media.movies.usecase.GetMovieReviewsUseCase
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getSeriesReviewsUseCase: GetSeriesReviewsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ReviewsScreenState, ReviewEffect>(initialState = ReviewsScreenState()) {

    init {
        val route = savedStateHandle.toRoute<ReviewRoute>()

        updateState {
            it.copy(
                isLoading = true,
                movieId = route.movieId,
                seriesId = route.seriesId
            )
        }

        state.value.movieId?.let {
            fetchMovieReviews(it)
        }

        state.value.seriesId?.let {
            fetchSeriesReviews(it)
        }
    }

    private fun fetchMovieReviews(movieId: Int) {
        safeExecute(
            onSuccess = ::onFetchReviewsSuccess,
            onError = ::handleError
        ) {
            val reviewsPager = Pager(
                config = PagingConfig(
                    pageSize = 15,
                    prefetchDistance = 5,
                    initialLoadSize = 15
                )
            ) {
                BasePagingSource { page ->
                    getMovieReviewsUseCase(
                        movieId = movieId,
                        pageNumber = page
                    )
                }
            }

            return@safeExecute reviewsPager
                .flow
                .cachedIn(viewModelScope)
        }
    }

    private fun fetchSeriesReviews(seriesId: Int) {
        safeExecute(
            onSuccess = ::onFetchReviewsSuccess,
            onError = ::handleError
        ) {
            val reviewsPager = Pager(
                config = PagingConfig(
                    pageSize = 15,
                    prefetchDistance = 5,
                    initialLoadSize = 15
                )
            ) {
                BasePagingSource { page ->
                    getSeriesReviewsUseCase(
                        seriesId = seriesId,
                        page = page
                    )
                }
            }

            return@safeExecute reviewsPager
                .flow
                .cachedIn(viewModelScope)
        }
    }

    private fun onFetchReviewsSuccess(reviews: Flow<PagingData<Review>>) {
        val reviewsUiFlow = reviews.map { pagingData ->
            pagingData.map { it.toReviewUI() }
        }
        updateState {
            it.copy(
                reviewsFlow = reviewsUiFlow,
                isLoading = false
            )
        }
    }

    private fun handleError(throwable: Throwable) {
        sendEffect(ReviewEffect.ShowError(throwable.message ?: "Unknown error"))
    }
}