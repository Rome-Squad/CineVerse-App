package com.giraffe.home.screen.show_more.top_rated_tv_shows

import com.giraffe.home.screen.show_more.BaseShowMoreViewModel
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedTvShowsViewModel @Inject constructor(
    private val getTopRatedTvShowsUsaCase: GetTopRatedSeriesUseCase,
) : BaseShowMoreViewModel() {

    init {
        loadData()
    }

    override fun loadData() {
        safeExecute(
            onSuccess = ::onGetTopRatedTvShowsSuccess,
            onError = ::onFail,
            block = {
                getTopRatedTvShowsUsaCase(
                    page = 1,
                    limit = 10
                )
            }
        )
    }

    private fun onGetTopRatedTvShowsSuccess(series: List<Series>) {
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = series.map { it.toPosterUi() })
        }
    }
}