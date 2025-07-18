package com.giraffe.explore.screen.searchresult

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.NothingFound
import com.giraffe.explore.components.TransitionLazyColumnToGrid
import com.giraffe.explore.screen.discover.SearchTab
import com.giraffe.media.explore.components.CastItem
import com.giraffe.media.explore.util.toTitle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchResultScreen(
    query: String,
    viewModel: SearchResultViewModel = koinViewModel { parametersOf(query) },
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    SearchResultContent(state, viewModel, navController::popBackStack)
}

@Composable
fun SearchResultContent(
    state: SearchResultScreenState,
    interactions: SearchResultInteractionListener,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
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
                        .fillParentMaxHeight(),
                ) {
                    if (state.selectedTab == SearchTab.ACTORS) {
                        if (state.actors.isNotEmpty()) {
                            ActorsSection(
                                actors = state.actors
                            )
                        } else {
                            NothingFound(
                                modifier = Modifier
                                    .padding(horizontal = 60.dp)
                                    .padding(top = 195.dp)
                            )
                        }
                    } else if (state.selectedPosters.isNotEmpty()) {
                        TransitionLazyColumnToGrid(
                            poster = state.selectedPosters,
                            isListSelected = !state.isGridSelected,
                            contentPadding = PaddingValues(vertical = 16.dp),
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
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 16.dp, end = 16.dp),
            visible = state.selectedTab != SearchTab.ACTORS,
            enter = slideInHorizontally { it * 2 },
            exit = slideOutHorizontally { it * 2 }
        ) {
            ViewToggle(
                isListSelected = !state.isGridSelected,
                onGridSelected = interactions::changeView,
            )
        }
    }
}

@Composable
fun ActorsSection(modifier: Modifier = Modifier, actors: List<ActorUi>) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = actors, key = { actor -> actor.id }) { actor ->
            CastItem(
                name = actor.name,
                imageUrl = actor.imageUrl,
            )
        }
    }

}