package com.giraffe.presentation.home.screen.categoryMedia


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.HorizontalDivider
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.components.ScreenState
import com.giraffe.presentation.home.components.TransitionLazyColumnToGrid
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.navigation.home.routes.CategoryMediaSectionType
import com.giraffe.presentation.home.utils.showToast
import com.giraffe.presentation.home.utils.toStringRes

@Composable
fun CategoryMediaScreen(
    onBackClick: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
    categoryMediaViewModel: CategoryMediaViewModel = hiltViewModel()
) {
    val state by categoryMediaViewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        categoryMediaViewModel.effect.collect { effect ->
            when (effect) {
                is CategoryMediaEffect.NavigateToMovieDetails -> navigateToMoviesDetailsScreen(
                    effect.movieId
                )

                is CategoryMediaEffect.NavigateToSeriesDetails -> navigateToSeriesDetailsScreen(
                    effect.seriesId
                )

                CategoryMediaEffect.NavigateBack -> onBackClick()

                is CategoryMediaEffect.ShowError -> context.showToast(effect.error.toStringRes())
            }
        }
    }
    CategoryMediaContent(
        state = state,
        interactionListener = categoryMediaViewModel,
    )
}

@Composable
fun CategoryMediaContent(
    state: CategoryMediaScreenState,
    interactionListener: CategoryMediaInteractionListener,
) {
    val lazyMediaPosters = state.mediaFlow.collectAsLazyPagingItems()

    ScreenState(
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet,
        onRetryClick = interactionListener::onRetryClick
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                AppBar(
                    title = state.sectionType?.getSectionTitle(LocalContext.current),
                    showBackButton = true,
                    onBackButtonClick = interactionListener::onBackClick
                )
                HorizontalDivider()
                Box(
                    Modifier
                        .fillMaxWidth()
                        .systemBarsPadding()
                ) {
                    TransitionLazyColumnToGrid(
                        posters = lazyMediaPosters,
                        isListSelected = state.isListSelected,
                        onClickItem = interactionListener::onMediaClicked,
                        contentPreference = state.contentPreference,
                    )
                }

            }
            ViewToggle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp),
                isListSelected = state.isListSelected,
                onGridSelected = interactionListener::onViewChanged
            )
        }
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun CategoryMediaPreview() {
    val interactionListener = object : CategoryMediaInteractionListener {
        override fun onViewChanged(isGrid: Boolean) {}
        override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {}
        override fun onBackClick() {}
        override fun onRetryClick() {}
    }
    CategoryMediaContent(
        state = CategoryMediaScreenState(
            sectionType = CategoryMediaSectionType.RECENTLY_RELEASED
        ),
        interactionListener = interactionListener,
    )
}