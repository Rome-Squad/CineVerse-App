package com.giraffe.explore.screen.searchresult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.components.CastItem
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.NothingFound
import com.giraffe.explore.components.TransitionLazyColumnToGrid
import com.giraffe.explore.screen.discover.SearchTab
import com.giraffe.explore.util.toTitle

@Composable
fun SearchResultScreen(
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: SearchResultViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchResultContent(
        state = state,
        interactions = viewModel,
        navigateToMovieDetails = navigateToMovieDetails,
        navigateToSeriesDetails = navigateToSeriesDetails,
        navigateToCastDetails = navigateToCastDetails,
        onBackClick = onBackClick,
    )
}

@Composable
private fun SearchResultContent(
    state: SearchResultScreenState,
    interactions: SearchResultInteractionListener,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
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
                    enabled = false,
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
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        posters.loadState.refresh is LoadState.Loading -> Progress(size = 32.dp)
                        posters.loadState.hasError && posters.itemCount == 0 -> NoInternetScreen(
                            onRetryClick = interactions::retry
                        )

                        posters.itemCount == 0 -> NothingFound()
                        else -> if (state.selectedTab == SearchTab.ACTORS) {
                            ActorsSection(
                                modifier = Modifier.fillMaxSize(),
                                actors = posters,
                                navigateToCastDetails = navigateToCastDetails
                            )
                        } else {
                            TransitionLazyColumnToGrid(
                                modifier = Modifier.fillMaxSize(),
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
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ActorsSection(
    modifier: Modifier = Modifier,
    actors: LazyPagingItems<Poster>,
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
                imageUrl = actors[actorIndex]?.imageUri.toString(),
                onClick = {
                    navigateToCastDetails(actors[actorIndex]?.id ?: 0)
                }
            )
        }
    }
}

