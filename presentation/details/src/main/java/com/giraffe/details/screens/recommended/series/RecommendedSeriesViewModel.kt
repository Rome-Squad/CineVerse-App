package com.giraffe.details.screens.recommended.series

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giraffe.details.base.BasePagingSource
import com.giraffe.details.models.SeriesUi
import com.giraffe.media.entity.Genre
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecommendedSeriesViewModel(
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getSeriesGenres: GetSeriesGenresUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), RecommendedInteractionListener {

    init {
        loadAllGenre()
    }

    private val _effect = Channel<RecommendedSeriesEffect>()
    val effect = _effect.receiveAsFlow()
    var allGenre = emptyList<Genre>()
    fun loadAllGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            allGenre = getSeriesGenres()
        }
    }

    val seriesId = savedStateHandle.get<Int>("seriesID") ?: 0

    val recommendationScreenState = Pager(config = PagingConfig(20)) {
        BasePagingSource { page -> getRecommendedSeries(seriesId.toLong(), page) }
    }
        .flow
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { series ->
                SeriesUi.fromEntity(series)
                    .copy(
                        genres = allGenre
                            .filter { it.id in series.genreIDs }
                            .map { it.title }
                    )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    override fun navigateToSeriesDetailsScreen(seriesId: Int) {
        viewModelScope.launch {
            _effect.send(
                RecommendedSeriesEffect.NavigateToSeriesDetails(seriesId)
            )
        }
    }

    override fun onCleared() {
        _effect.close()
        super.onCleared()
    }
}