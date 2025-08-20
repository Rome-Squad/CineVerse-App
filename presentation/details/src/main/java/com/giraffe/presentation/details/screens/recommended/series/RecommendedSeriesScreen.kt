package com.giraffe.presentation.details.screens.recommended.series

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.presentation.details.base.BaseScreen
import com.giraffe.presentation.details.components.TransitionLazyColumnToGridPoster
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.showToast
import com.giraffe.presentation.details.utils.toStringResource

@Composable
fun RecommendedSeriesScreen(
    navigateToSeriesDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: RecommendedSeriesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    EventListener(
        events = viewModel.effect,
    ) { effect ->
        when (effect) {
            is RecommendedSeriesEffect.NavigateToSeriesDetails -> {
                navigateToSeriesDetails(effect.seriesId)
            }

            is RecommendedSeriesEffect.Error -> context.showToast(
                effect.error.toStringResource()
            )

            RecommendedSeriesEffect.NavigateBack -> onBackClick()
        }
    }

    RecommendedSeriesContent(
        state = state,
        interaction = viewModel,
    )
}

@Composable
private fun RecommendedSeriesContent(
    state: RecommendedSeriesScreenState,
    interaction: RecommendedInteractionListener,
) {
    var isGridSelected by rememberSaveable { mutableStateOf(true) }
    val lazyPagingItems = state.recommendedSeriesFlow.collectAsLazyPagingItems()

    BaseScreen(
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onRetryClick = interaction::onRetryClick,
        title = state.seriesTitle.orEmpty(),
        onBackClick = interaction::onBackClick,
        hasHorizontalDivider = true
    ) {
        Box(Modifier.fillMaxSize()) {
            TransitionLazyColumnToGridPoster(
                lazyPagingItems = lazyPagingItems,
                isListSelected = !isGridSelected,
                onItemClick = interaction::onSeriesClick
            )

            ViewToggle(
                isListSelected = !isGridSelected,
                onGridSelected = { isGridSelected = !isGridSelected },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp)
            )
        }
    }
}