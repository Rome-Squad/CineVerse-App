package com.giraffe.presentation.details.screens.recommended.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.HorizontalDivider
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.base.ScreenStates
import com.giraffe.presentation.details.components.TransitionLazyColumnToGridPoster
import com.giraffe.presentation.details.utils.EventListener
import com.giraffe.presentation.details.utils.showToast
import com.giraffe.presentation.details.utils.toStringResource

@Composable
fun RecommendedMoviesScreen(
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
) {
    val viewModel: RecommendedMoviesViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    EventListener(
        events = viewModel.effect,
    ) { effect ->
        when (effect) {
            is RecommendedMoviesEffect.NavigateToMovieDetails -> {
                navigateToMovieDetails(effect.movieId)
            }

            is RecommendedMoviesEffect.ShowError -> context.showToast(
                effect.error.toStringResource()
            )

            RecommendedMoviesEffect.NavigateBack -> onBackClick()
        }
    }

    RecommendedMovieContent(
        state = state,
        interaction = viewModel
    )

}


@Composable
fun RecommendedMovieContent(
    state: RecommendedMoviesScreenState,
    interaction: RecommendedInteractionListener
) {
    var isListSelected by rememberSaveable { mutableStateOf(false) }
    val lazyPagingItems = state.recommendedMoviesFlow.collectAsLazyPagingItems()

    ScreenStates(
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onRetryClick = interaction::onRetryClick
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                AppBar(
                    title = state.movieTitle.orEmpty(),
                    showBackButton = true,
                    onBackButtonClick = interaction::onBackClick
                )
                HorizontalDivider()
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                ) {
                    TransitionLazyColumnToGridPoster(
                        lazyPagingItems = lazyPagingItems,
                        isListSelected = isListSelected,
                        onItemClick = interaction::onMovieClick
                    )
                }

            }

            ViewToggle(
                isListSelected = isListSelected,
                onGridSelected = { isListSelected = !it },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp)
            )
        }
    }
}

