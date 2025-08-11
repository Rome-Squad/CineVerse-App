package com.giraffe.presentation.details.screens.recommended.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.base.BaseScreen
import com.giraffe.presentation.details.model.MovieUi
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
        interaction = viewModel,
    )

}


@Composable
fun RecommendedMovieContent(
    state: RecommendedMoviesScreenState,
    interaction: RecommendedInteractionListener
) {
    var isGridSelected by rememberSaveable { mutableStateOf(false) }
    val lazyPagingItems = state.recommendedMoviesFlow.collectAsLazyPagingItems()

    BaseScreen(
        title = state.movieTitle.orEmpty(),
        caption = stringResource(R.string.because_you_watched),
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onBackClick = interaction::onBackClick,
        onRetryClick = interaction::onRetryClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CardsSection(
                modifier = Modifier
                    .fillMaxSize(),
                lazyPagingItems = lazyPagingItems,
                isGridSelected = !isGridSelected,
                onItemClick = interaction::onMovieClick
            )

            ViewToggle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(16.dp),
                isListSelected = isGridSelected,
                onGridSelected = { isGridSelected = !it }
            )
        }
    }


}


@Composable
private fun CardsSection(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<MovieUi>,
    isGridSelected: Boolean,
    onItemClick: (Int) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        TransitionLazyColumnToGridMovie(
            lazyPagingItems = lazyPagingItems,
            isListSelected = !isGridSelected,
            contentPadding = PaddingValues(vertical = 5.dp),
            onItemClick = onItemClick
        )
    }
}

