package com.giraffe.home.screen.show_more

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
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.components.ListTitleSection
import com.giraffe.home.components.TransitionLazyColumnToGrid
import com.giraffe.home.screen.home.MediaType

@Composable
fun ShowMoreScreen(
    showMoreViewModel: ShowMoreViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    navigateToMoviesDetailsScreen: (Int) -> Unit,
    navigateToSeriesDetailsScreen: (Int) -> Unit,
) {
    val state by showMoreViewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        showMoreViewModel.effect.collect { effect ->
            when (effect) {
                is ShowMoreEffect.NavigateToMovieDetails -> {
                    navigateToMoviesDetailsScreen(effect.movieId)
                }

                is ShowMoreEffect.NavigateToSeriesDetails -> {
                    navigateToSeriesDetailsScreen(effect.seriesId)
                }

                is ShowMoreEffect.ShowError -> {}
            }
        }
    }
    ShowMoreContent(
        state = state,
        showMoreInteractionListener = showMoreViewModel,
        onBackClick = onBackClick,
    )
}

@Composable
fun ShowMoreContent(
    state: ShowMoreState,
    showMoreInteractionListener: ShowMoreInteractionListener,
    onBackClick: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier
                .background(Theme.color.background.screen)
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            ListTitleSection(
                title = state.sectionType?.getSectionTitle(LocalContext.current)?:"",
                onBackClick = onBackClick
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TransitionLazyColumnToGrid(
                    poster = state.mediaList,
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


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun ShowMorePreview() {
    val interactionListener = object : ShowMoreInteractionListener {
        override fun onViewChanged(isGrid: Boolean) {}
        override fun onMediaClicked(mediaId: Int, mediaType: MediaType) {}
    }
    ShowMoreContent(
        state = ShowMoreState(
            sectionType = ShowMoreSectionType.RECENTLY_RELEASED
        ),
        showMoreInteractionListener = interactionListener,
        onBackClick = {},
    )
}