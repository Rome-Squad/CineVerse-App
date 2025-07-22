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
import com.giraffe.media.series.usecase.GetRecommendedSeriesUseCase
import com.giraffe.media.series.usecase.GetSeriesGenresByIdsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecommendedSeriesViewModel(
    private val getRecommendedSeries: GetRecommendedSeriesUseCase,
    private val getSeriesGenres: GetSeriesGenresByIdsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), RecommendedInteractionListener {

    private val _effect = Channel<RecommendedSeriesEffect>()
    val effect = _effect.receiveAsFlow()

    val seriesId = savedStateHandle.get<Int>("seriesID") ?: 268

    val recommendationScreenState = Pager(config = PagingConfig(20)) {
        BasePagingSource { page -> getRecommendedSeries(seriesId.toLong(), page) }
    }
        .flow
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { series ->
//                val genres = getSeriesGenres(series.genreIDs).map { genre-> }
                SeriesUi.fromEntity(series)//.copy(genres = genres)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    override fun navigateToSeriesDetailsScreen(seriesId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
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