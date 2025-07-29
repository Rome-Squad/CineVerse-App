package com.giraffe.details.screens.reviewScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giraffe.details.models.ReviewUI
import com.giraffe.details.models.toReviewUI
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.series.repository.SeriesRepository
import kotlinx.coroutines.launch

class ReviewsViewModel(
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private val _reviewsState = mutableStateOf<List<ReviewUI>>(emptyList())
    val reviewsState: State<List<ReviewUI>> get() = _reviewsState

    fun fetchReviews(movieId: Int? = null, seriesId: Int? = null) {
        viewModelScope.launch {
            _reviewsState.value = when {
                movieId != null -> {
                    moviesRepository.getMovieReviews(movieId).map { it.toReviewUI() }
                }
                seriesId != null -> {
                    seriesRepository.getSeriesReviews(seriesId).map { it.toReviewUI() }
                }
                else -> emptyList()
            }
        }
    }
}