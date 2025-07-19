package com.giraffe.details.screens.recommended.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.giraffe.details.base.BasePagingSource
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.MovieUi
import com.giraffe.details.screens.recommended.RecommendedEffect
import com.giraffe.details.screens.recommended.RecommendedInteractionListener
import com.giraffe.details.screens.recommended.RecommendedScreenState
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RecommendedMoviesViewModel(
    val title: String = "",
    private val movieId: Int,
    private val getRecommendedMovies: GetRecommendedMovieUseCase,
) : BaseViewModel<RecommendedScreenState, RecommendedEffect>(
    RecommendedScreenState()
), RecommendedInteractionListener {

    val recommendationScreenState = Pager(config = PagingConfig(20)) {
        BasePagingSource { page -> getRecommendedMovies(movieId, page) }
    }
        .flow
        .map { it.map(MovieUi::fromEntity) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}
