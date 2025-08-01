package com.giraffe.details.screens.reviewScreen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.models.toReviewUI
import com.giraffe.media.entity.Review
import com.giraffe.media.movies.usecase.GetMovieReviewsUseCase
import com.giraffe.media.series.usecase.GetSeriesReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ReviewsUiState(
    val reviews: List<ReviewUI> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getSeriesReviewsUseCase: GetSeriesReviewsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ReviewsUiState, ReviewEffect>(initialState = ReviewsUiState()) {
    private val route = savedStateHandle.toRoute<ReviewRoute>()
    private val movieId = route.movieId
    private val seriesId = route.seriesId

    init {
        movieId?.let {
            fetchMovieReviews(it)
        }
        seriesId?.let {
            fetchSeriesReviews(it)
        }
    }

    private fun fetchMovieReviews(movieId: Int) {
        safeExecute(
            onError = ::handleError,
            onSuccess = ::onFetchReviewsSuccess
        ) {
            getMovieReviewsUseCase.invoke(movieId = movieId, pageNumber = 1, pageSize = 1)
        }

    }

    private fun fetchSeriesReviews(seriesId: Int) {
        safeExecute(
            onError = ::handleError,
            onSuccess = ::onFetchReviewsSuccess
        ) {
            getSeriesReviewsUseCase.invoke(seriesId)
        }

    }

    private fun onFetchReviewsSuccess(reviews: List<Review>) {
        updateState {
            it.copy(
                reviews = reviews.map(Review::toReviewUI),
                isLoading = false
            )
        }
    }

    private fun handleError(throwable: Throwable) {
        sendEffect(ReviewEffect.ShowError(throwable.message ?: "Unknown error"))
    }
}