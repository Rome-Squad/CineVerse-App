package com.giraffe.presentation.explore.screen.searchresult

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.explore.components.ExploreHeader
import com.giraffe.presentation.explore.components.TransitionLazyColumnToGrid
import com.giraffe.presentation.explore.screen.discover.SearchTab
import com.giraffe.presentation.explore.screen.searchresult.components.ActorsSection
import com.giraffe.presentation.explore.screen.searchresult.components.NothingFound
import com.giraffe.presentation.explore.util.EffectListener
import com.giraffe.presentation.explore.util.mapExceptionToStringRes
import com.giraffe.presentation.explore.util.showToast
import com.giraffe.presentation.explore.util.toTitle

@Composable
fun SearchResultScreen(
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToCastDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: SearchResultViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            is SearchResultEffect.ShowError -> context.showToast(mapExceptionToStringRes(effect.error))
            is SearchResultEffect.NavigateToCastDetails -> navigateToCastDetails(effect.personId)
            is SearchResultEffect.NavigateToMovieDetail -> navigateToMovieDetails(effect.movieId)
            is SearchResultEffect.NavigateToSeriesDetail -> navigateToSeriesDetails(effect.seriesId)
            is SearchResultEffect.NavigateBack -> onBackClick()
        }
    }

    SearchResultContent(
        state = state,
        interactions = viewModel,
    )
}

@Composable
private fun SearchResultContent(
    state: SearchResultScreenState,
    interactions: SearchResultInteractionListener,
) {
    val context = LocalContext.current
    val posters = state.selectedPosters.collectAsLazyPagingItems()

    if (state.isNoInternet) NoInternetScreen(onRetryClick = interactions::retry)
    else {
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
                        onTextFieldClicked = interactions::onBackClick,
                        onBackClick = interactions::onBackClick
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
                            state.isLoading -> Box(
                                Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) { Progress() }

                            else -> if (state.selectedTab == SearchTab.ACTORS) {
                                ActorsSection(
                                    modifier = Modifier.fillMaxSize(),
                                    actors = posters,
                                    navigateToCastDetails = { id ->
                                        interactions.onPosterClick(id, SearchTab.ACTORS)
                                    }
                                )
                            } else {
                                TransitionLazyColumnToGrid(
                                    modifier = Modifier.fillMaxSize(),
                                    posters = posters,
                                    isListSelected = !state.isGridSelected,
                                    onPosterClicked = { id ->
                                        interactions.onPosterClick(id, state.selectedTab)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = state.selectedTab != SearchTab.ACTORS,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                ViewToggle(
                    isListSelected = !state.isGridSelected,
                    onGridSelected = interactions::changeView
                )
            }
        }
    }
}