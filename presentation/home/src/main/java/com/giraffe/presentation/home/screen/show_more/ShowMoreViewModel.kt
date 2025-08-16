package com.giraffe.presentation.home.screen.show_more


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.giraffe.presentation.home.base.BasePagingSource
import com.giraffe.presentation.home.base.BaseViewModel
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.model.ShowMorePoster
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreRoute
import com.giraffe.user.exception.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ShowMoreViewModel @Inject constructor(
    private val showMoreFactory: MixedMediaFactory,
    stateSavedStateHandle: SavedStateHandle
) : BaseViewModel<ShowMoreScreenState, ShowMoreEffect>(ShowMoreScreenState()),
    ShowMoreInteractionListener {

    private val sectionType = stateSavedStateHandle.toRoute<ShowMoreRoute>().sectionType

    init {
        loadByStrategy()
    }

    private fun loadByStrategy() {
        safeExecute(
            onError = ::onLoadByStrategyFail,
            onSuccess = ::onLoadByStrategySuccess,
        ) {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    prefetchDistance = PREFETCH_DISTANCE,
                    initialLoadSize = INITIAL_LOAD_SIZE
                )
            ) {
                BasePagingSource(
                    onError = ::onLoadByStrategyFail
                ) { page ->
                    showMoreFactory.createStrategy(sectionType).loadData(page, PAGE_SIZE)
                }
            }

            pager
                .flow
                .cachedIn(viewModelScope)
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    PagingData.empty()
                )

        }
    }

    private fun onLoadByStrategySuccess(mediaFlow: Flow<PagingData<ShowMorePoster>>) {
        updateState {
            it.copy(
                sectionType = sectionType,
                mediaFlow = mediaFlow,
                isLoading = false,
                isNoInternet = false
            )
        }
    }

    private fun onLoadByStrategyFail(exception: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
                isNoInternet = exception is NoInternetException
            )
        }
        sendEffect(ShowMoreEffect.ShowError(exception))
    }


    override fun onViewChanged(isGrid: Boolean) {
        updateState {
            it.copy(isListSelected = !isGrid)
        }
    }

    override fun onMediaClicked(
        mediaId: Int,
        mediaType: MediaType
    ) {
        when (mediaType) {
            MediaType.MOVIE -> sendEffect(ShowMoreEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(ShowMoreEffect.NavigateToSeriesDetails(mediaId))
        }
    }

    override fun onBackClick() {
        sendEffect(ShowMoreEffect.NavigateBack)
    }

    companion object {
        private const val PAGE_SIZE = 15
        private const val PREFETCH_DISTANCE = 5
        private const val INITIAL_LOAD_SIZE = 15
    }
}