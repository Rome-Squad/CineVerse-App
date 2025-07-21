package com.giraffe.details.screens.recommended.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.details.base.BasePagingSource
import com.giraffe.details.base.BaseViewModel
import com.giraffe.details.models.MovieUi
import com.giraffe.details.screens.recommended.RecommendedEffect
import com.giraffe.details.screens.recommended.RecommendedInteractionListener
import com.giraffe.details.screens.recommended.RecommendedScreenState
import com.giraffe.media.movies.usecase.GetMovieGenresUseCase
import com.giraffe.media.movies.usecase.GetRecommendedMovieUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RecommendedMoviesViewModel(
    savedStateHandle: SavedStateHandle,
    private val getRecommendedMovies: GetRecommendedMovieUseCase,
    private val getMovieGenres: GetMovieGenresUseCase
) : BaseViewModel<RecommendedScreenState, RecommendedEffect>(
    RecommendedScreenState()
), RecommendedInteractionListener {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])
    val title: String = savedStateHandle["title"] ?: ""

    val recommendationScreenState = Pager(config = PagingConfig(20)) {
        BasePagingSource { page -> getRecommendedMovies(movieId, page) }
    }
        .flow
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { movie ->
                val genres = getMovieGenres(movie.genresID).map { it.title }
                MovieUi.fromEntity(movie).copy(genres = genres)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
}
