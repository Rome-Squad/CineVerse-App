package com.giraffe.presentation.details.screens.recommended.series

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.model.SeriesUi
import com.giraffe.presentation.details.utils.EventListener

@Composable
fun RecommendedSeriesScreen(
    navigateToSeriesDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendedSeriesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val lazyPagingItems = state.recommendedSeriesFlow.collectAsLazyPagingItems()

    EventListener(
        events = viewModel.effect,
    ) { effect ->
        when (effect) {
            is RecommendedSeriesEffect.NavigateToSeriesDetails -> {
                navigateToSeriesDetails(effect.seriesId)
            }
        }
    }

    RecommendedSeriesContent(
        title = state.seriesTitle.orEmpty(),
        lazyPagingItems = lazyPagingItems,
        onBackButtonClick = onBackClick,
        interaction = viewModel,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .systemBarsPadding()
    )
}

@Composable
private fun RecommendedSeriesContent(
    title: String,
    lazyPagingItems: LazyPagingItems<SeriesUi>,
    onBackButtonClick: () -> Unit,
    interaction: RecommendedInteractionListener,
    modifier: Modifier = Modifier,
) {
    var isGridSelected by rememberSaveable { mutableStateOf(true) }

    Box {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Theme.color.background.screen)
                .statusBarsPadding(),
        ) {
            AppBar(
                title = title,
                caption = stringResource(R.string.because_you_watched),
                showBackButton = true,
                modifier = Modifier.padding(8.dp),
                onBackButtonClick = onBackButtonClick,
            )

            TransitionBetweenLazyColumnToLazyVerticalGridSeries(
                lazyPagingItems = lazyPagingItems,
                isListSelected = !isGridSelected,
                contentPadding = PaddingValues(16.dp),
                onItemClick = interaction::navigateToSeriesDetailsScreen
            )
        }

        ViewToggle(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(16.dp),
            isListSelected = !isGridSelected,
            onGridSelected = { isGridSelected = !isGridSelected }
        )
    }
}