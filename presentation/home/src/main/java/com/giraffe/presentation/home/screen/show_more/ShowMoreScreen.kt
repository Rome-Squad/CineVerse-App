package com.giraffe.presentation.home.screen.show_more


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.home.components.BaseScreenWithStates
import com.giraffe.presentation.home.components.HorizontalDivider
import com.giraffe.presentation.home.components.TransitionLazyColumnToGrid
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.navigation.home.routes.ShowMoreSectionType
import com.giraffe.presentation.home.utils.showToast
import com.giraffe.presentation.home.utils.toStringRes

@Composable
fun ShowMoreScreen(
    showMoreViewModel: ShowMoreViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by showMoreViewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        showMoreViewModel.effect.collect { effect ->
            when (effect) {
                is ShowMoreEffect.NavigateToMovieDetails -> navigateToMoviesDetailsScreen(effect.movieId)

                is ShowMoreEffect.NavigateToSeriesDetails -> navigateToSeriesDetailsScreen(effect.seriesId)

                ShowMoreEffect.NavigateBack -> onBackClick()

                is ShowMoreEffect.ShowError -> context.showToast(effect.error.toStringRes())
            }
        }
    }
    ShowMoreContent(
        state = state,
        showMoreInteractionListener = showMoreViewModel,
    )
}

@Composable
fun ShowMoreContent(
    state: ShowMoreScreenState,
    showMoreInteractionListener: ShowMoreInteractionListener,
) {
    val lazyMediaPosters = state.mediaFlow.collectAsLazyPagingItems()
    BaseScreenWithStates(
        isLoading = state.isLoading,
        isNoInternet = state.isNoInternet
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                AppBar(
                    title = state.sectionType?.getSectionTitle(LocalContext.current) ?: "",
                    showBackButton = true,
                    onBackButtonClick = showMoreInteractionListener::onBackClick
                )
                HorizontalDivider()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TransitionLazyColumnToGrid(
                        posters = lazyMediaPosters,
                        isListSelected = state.isListSelected,
                        onClickItem = showMoreInteractionListener::onMediaClicked,
                    )
                }

            }
            ViewToggle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp),
                isListSelected = state.isListSelected,
                onGridSelected = showMoreInteractionListener::onViewChanged
            )
        }
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun ShowMorePreview() {
    val interactionListener = object : ShowMoreInteractionListener {
        override fun onViewChanged(isGrid: Boolean) {}
        override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {}
        override fun onBackClick() {}
    }
    ShowMoreContent(
        state = ShowMoreScreenState(
            sectionType = ShowMoreSectionType.RECENTLY_RELEASED
        ),
        showMoreInteractionListener = interactionListener,
    )
}