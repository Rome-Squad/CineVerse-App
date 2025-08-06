package com.giraffe.home.screen.show_more.top_rated_tv_shows

import com.giraffe.home.base.BaseViewModel
import com.giraffe.home.screen.home.MediaType
import com.giraffe.home.screen.show_more.ShowMoreEffect
import com.giraffe.home.screen.show_more.ShowMoreInteractionListener
import com.giraffe.home.screen.show_more.ShowMoreUiState
import com.giraffe.home.utils.toPosterUi
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.usecase.GetTopRatedSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedTvShowsViewModel @Inject constructor(
    private val getTopRatedTvShowsUsaCase: GetTopRatedSeriesUseCase,
) : BaseViewModel<ShowMoreUiState, ShowMoreEffect>(ShowMoreUiState()), ShowMoreInteractionListener {

    init {
        getTopRatedTvShows()
    }

    private fun getTopRatedTvShows() {
        safeExecute(
            onSuccess = ::getTopRatedTvShowsSuccess,
            onError = ::onFail,
            block = { getTopRatedTvShowsUsaCase(page = 1, limit = 10) }
        )
    }

    private fun getTopRatedTvShowsSuccess(tvShows: List<Series>) {
        val topRatedTvShowsUi = tvShows.map { series ->
            series.toPosterUi()
        }
        updateState {
            it.copy(
                isLoading = false,
                errorMsgRes = null,
                mediaList = topRatedTvShowsUi,
            )
        }
    }

    private fun onFail(errorMsgRes: Int) {
        updateState { it.copy(isLoading = false, errorMsgRes = errorMsgRes) }
    }

    override fun onViewChanged(isGrid: Boolean) {
        updateState {
            it.copy(isListSelected = !isGrid)
        }
    }

    override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {
        sendEffect(ShowMoreEffect.NavigateToSeriesDetails(mediaId))
    }

}