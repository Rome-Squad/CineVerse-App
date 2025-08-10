package com.giraffe.presentation.details.screens.recommended.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.usecase.GetMoviesGenresByIdsUseCase
import com.giraffe.media.movie.usecase.GetRecommendedMoviesUseCase
import com.giraffe.presentation.details.base.BasePagingSource
import com.giraffe.presentation.details.model.MovieUi
import com.giraffe.presentation.details.model.toMovieUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedMoviesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecommendedMovies: GetRecommendedMoviesUseCase,
    private val getMoviesGenresByIds: GetMoviesGenresByIdsUseCase
) : ViewModel(), RecommendedInteractionListener {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])
    val title: String = savedStateHandle.get<String>("title").orEmpty()
    private val _effect = Channel<RecommendedEffectMovie>()
    val effect = _effect.receiveAsFlow()
    val recommendationScreenState = Pager(config = PagingConfig(20)) {
        BasePagingSource { page -> getRecommendedMovies(movieId, page) }
    }
        .flow
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { movie -> mapMovieToMovieUi(movie) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private suspend fun mapMovieToMovieUi(movie: Movie): MovieUi {
        val movieUi = movie.toMovieUi()
        val genres = getMoviesGenresByIds(movieUi.genresID).map { it.title }
        return movieUi.copy(genres = genres)
    }


    override fun navigateToMovieDetailsScreen(movieId: Int) {
        viewModelScope.launch {
            _effect.send(
                RecommendedEffectMovie.NavigateToMovieDetails(movieId)
            )
        }
    }

}