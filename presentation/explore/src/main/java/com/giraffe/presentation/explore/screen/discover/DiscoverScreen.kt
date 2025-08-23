package com.giraffe.presentation.explore.screen.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.explore.R
import com.giraffe.presentation.explore.components.ExploreHeader
import com.giraffe.presentation.explore.model.GenreUi
import com.giraffe.presentation.explore.screen.discover.components.GenresAndCardsSection
import com.giraffe.presentation.explore.util.EffectListener
import com.giraffe.presentation.explore.util.mapExceptionToStringRes
import com.giraffe.presentation.explore.util.showToast
import com.giraffe.presentation.explore.util.toTitle

@Composable
fun DiscoverScreen(
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: DiscoverViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            is DiscoverEffect.NavigateToMovieDetails -> navigateToMovieDetails(effect.movieId)
            is DiscoverEffect.NavigateToSeriesDetails -> navigateToSeriesDetails(effect.seriesId)
            is DiscoverEffect.NavigateToSearchScreen -> navigateToSearch()
            is DiscoverEffect.ShowError -> context.showToast(mapExceptionToStringRes(effect.error))
        }
    }

    ExploreContent(
        state = state,
        interactions = viewModel,
    )
}

@Composable
fun ExploreContent(
    state: DiscoverScreenState,
    interactions: DiscoverInteractionListener,
) {
    val context = LocalContext.current
    val posters = state.selectedPosters.collectAsLazyPagingItems()


    if (state.isNoInternet)
        NoInternetScreen(onRetryClick = interactions::onRetryClick)
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
                        placeholder = stringResource(R.string.search),
                        enabled = false,
                        readOnly = true,
                        onTextFieldClicked = interactions::onSearchClick,
                        onEndIconClick = interactions::onSearchClick
                    )
                }
                stickyHeader {
                    Tabs(
                        modifier = Modifier.background(Theme.color.background.screen),
                        titles = state.tabs.map { it.toTitle(context) },
                        selectedTabIndex = state.selectedTab.ordinal,
                        onTabSelected = interactions::onTabSelected
                    )
                }
                when {
                    state.isLoading -> {
                        item {
                            Box(
                                Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) { Progress() }
                        }
                    }

                    else -> {
                        item {
                            GenresAndCardsSection(
                                modifier = Modifier.fillParentMaxHeight(),
                                onPosterClicked = { id ->
                                    interactions.onPosterClick(id, state.selectedTab)
                                },
                                posters = posters,
                                selectedGenre = state.selectedGenre
                                    ?: GenreUi(title = stringResource(R.string.all)),
                                genres = listOf(GenreUi(title = stringResource(R.string.all))) + state.selectedGenres,
                                isGridSelected = state.isGridSelected,
                                onGenreSelected = interactions::onGenreSelected,
                                contentPreference = state.contentPreference
                            )
                        }
                    }

                }
            }

            ViewToggle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                isListSelected = !state.isGridSelected,
                onGridSelected = interactions::onViewChanged,
            )
        }
    }
}