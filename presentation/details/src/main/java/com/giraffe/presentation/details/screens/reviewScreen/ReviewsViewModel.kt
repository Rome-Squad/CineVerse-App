package com.giraffe.presentation.details.screens.reviewScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.presentation.details.base.BasePagingSource
import com.giraffe.presentation.details.base.BaseViewModel
import com.giraffe.presentation.details.model.toReviewUI
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
            fetchPagingReviews { page ->
                getMovieReviewsUseCase(
                    movieId = movieId,
                    pageNumber = page
                )
            }
        }
    }

    private fun fetchSeriesReviews(seriesId: Int) {
        safeExecute(
            onSuccess = ::onFetchReviewsSuccess,
            onError = ::handleError
        ) {
            fetchPagingReviews { page ->
                getSeriesReviewsUseCase(
                    seriesId = seriesId,
                    page = page
                )
            }
        }
    }

    private fun fetchPagingReviews(
        pagingSource: suspend (page: Int) -> List<Review>
    ): Flow<PagingData<Review>> {
        val reviewsPager = Pager(
            config = PagingConfig(
                pageSize = 15,
                prefetchDistance = 5,
                initialLoadSize = 15
            )
        ) {
            BasePagingSource { page ->
                pagingSource(page)
            }
        }

        return reviewsPager
            .flow
            .cachedIn(viewModelScope)
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
        sendEffect(ReviewEffect.ShowError(mapErrorToResource(throwable)))
    }
}