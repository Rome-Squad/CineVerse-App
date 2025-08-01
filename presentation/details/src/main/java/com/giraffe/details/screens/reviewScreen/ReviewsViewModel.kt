package com.giraffe.details.screens.reviewScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.models.toReviewUI
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.movies.usecase.GetMovieDetailsUseCase
import com.giraffe.media.series.repository.SeriesRepository
import com.giraffe.media.series.usecase.GetSeriesDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase
) : BaseViewModel<List<ReviewUI>, ReviewEffect>(initialState = emptyList()) {

    fun fetchMovieReviews(movieId: Int) {
        safeExecute(
            onError = {},
            onSuccess = {}
        ) {
            getMovieDetailsUseCase(movieId)
        }

    }

    fun fetchSeriesReviews(seriesId: Int) {
        safeExecute(
            onError = {},
            onSuccess = {}
        ){
            getSeriesDetailsUseCase(seriesId)
        }

    }

    // Error handling, send an effect to notify the UI
    private fun handleError(throwable: Throwable) {
        sendEffect(ReviewEffect.ShowError(throwable.message ?: "Unknown error"))
    }
}