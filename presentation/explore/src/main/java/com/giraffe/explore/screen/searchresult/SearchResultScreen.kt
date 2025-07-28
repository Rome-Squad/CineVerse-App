package com.giraffe.explore.screen.searchresult

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.explore.components.CastItem
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.NothingFound
import com.giraffe.explore.components.TransitionLazyColumnToGrid
import com.giraffe.explore.screen.discover.SearchTab
import com.giraffe.explore.util.toTitle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchResultScreen(
    query: String,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: SearchResultViewModel = koinViewModel { parametersOf(query) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchResultContent(
        state = state,
        interactions = viewModel,
        navigateToMovieDetails = navigateToMovieDetails,
        navigateToSeriesDetails = navigateToSeriesDetails,
        navigateToCastDetails = navigateToCastDetails,
        onBackClick = onBackClick
    )
}

@Composable
fun SearchResultContent(
    state: SearchResultScreenState,
    interactions: SearchResultInteractionListener,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val actors = state.actors.collectAsLazyPagingItems()
    val posters = state.selectedPosters.collectAsLazyPagingItems()

    Box {
        LazyColumn(
            modifier = Modifier
                .background(Theme.color.background.screen)
                .statusBarsPadding()
                .padding(top = 16.dp),
        ) {
            item {
                ExploreHeader(
                    modifier = Modifier.padding(bottom = 12.dp),
                    endIcon = painterResource(Theme.icons.outline.microphone),
                    readOnly = true,
                    showBackButton = true,
                    value = state.query,
                    onTextFieldClicked = onBackClick,
                    onBackClick = onBackClick
                )
            }
            stickyHeader {
                Tabs(
                    modifier = Modifier.background(Theme.color.background.screen),
                    titles = state.tabs.map { it.toTitle(context) },
                    selectedTabIndex = state.selectedTab.ordinal,
                    onTabSelected = interactions::selectTap
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize(),
                ) {
                    // If network is unavailable
                    if (!state.isNetworkAvailable) {
                        NoInternetConnection(modifier = Modifier.padding(16.dp))
                    } else {
                        if (posters.loadState.refresh is LoadState.Loading) {
                            Progress(
                                size = 32.dp,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 16.dp)
                            )
                        } else {
                            if (state.selectedTab == SearchTab.ACTORS) {
                                if (actors.itemCount != 0) {
                                    ActorsSection(
                                        actors = actors,
                                        navigateToCastDetails = navigateToCastDetails
                                    )
                                } else {
                                    NothingFound(
                                        modifier = Modifier
                                            .padding(horizontal = 60.dp)
                                            .padding(top = 195.dp)
                                    )
                                }
                            } else if (posters.itemCount != 0) {
                                TransitionLazyColumnToGrid(
                                    posters = posters,
                                    isListSelected = !state.isGridSelected,
                                    onPosterClicked = { id ->
                                        when (state.selectedTab) {
                                            SearchTab.MOVIES -> navigateToMovieDetails(id)
                                            SearchTab.SERIES -> navigateToSeriesDetails(id)
                                            SearchTab.ACTORS -> navigateToCastDetails(id)
                                        }
                                    }
                                )
                            } else {
                                NothingFound(
                                    modifier = Modifier
                                        .padding(horizontal = 60.dp)
                                        .padding(top = 195.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ActorsSection(
    modifier: Modifier = Modifier,
    actors: LazyPagingItems<ActorUi>,
    navigateToCastDetails: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(actors.itemCount) { actorIndex ->
            CastItem(
                name = actors[actorIndex]?.name.toString(),
                imageUrl = actors[actorIndex]?.imageUrl.toString(),
                onClick = {
                    navigateToCastDetails(actors[actorIndex]?.id ?: 0)
                }
            )
        }
    }

}



@Composable
fun NoInternetConnection(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "No Internet Connection",
        )
    }
}