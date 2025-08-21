package com.giraffe.presentation.home.screen.categoryMedia


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
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaRoute
import com.giraffe.user.exception.NoInternetException
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoryMediaViewModel @Inject constructor(
    private val categoryMediaFactory: CategoryMediaFactory,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase,
    stateSavedStateHandle: SavedStateHandle
) : BaseViewModel<CategoryMediaScreenState, CategoryMediaEffect>(CategoryMediaScreenState()),
    CategoryMediaInteractionListener {

    private val sectionType = stateSavedStateHandle.toRoute<CategoryMediaRoute>().sectionType

    init {
        observeContentPreference()
        loadCategoryMediaDate()
    }

    private fun loadCategoryMediaDate() {
        updateState {
            it.copy(
                isLoading = true,
                isNoInternet = false
            )
        }
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
                    categoryMediaFactory.createStrategy(sectionType).loadData(page, PAGE_SIZE)
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

    private fun onLoadByStrategySuccess(mediaFlow: Flow<PagingData<PosterMedia>>) {
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
        if (exception is NoInternetException) {
            sendEffect(CategoryMediaEffect.ShowError(exception))
        }
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
            MediaType.MOVIE -> sendEffect(CategoryMediaEffect.NavigateToMovieDetails(mediaId))
            MediaType.SERIES -> sendEffect(CategoryMediaEffect.NavigateToSeriesDetails(mediaId))
        }
    }
    private fun observeContentPreference() {
        safeCollect(
            onEmitNewValue = { preference ->
                updateState { it.copy(contentPreference = preference) }
            },
            block = getContentPreferenceUseCase::invoke
        )
    }
    override fun onBackClick() {
        sendEffect(CategoryMediaEffect.NavigateBack)
    }

    override fun onRetryClick() {
        loadCategoryMediaDate()
    }

    companion object {
        private const val PAGE_SIZE = 15
        private const val PREFETCH_DISTANCE = 5
        private const val INITIAL_LOAD_SIZE = 15
    }
}