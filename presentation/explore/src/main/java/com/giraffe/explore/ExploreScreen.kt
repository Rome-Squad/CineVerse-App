package com.giraffe.explore

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Chip
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.explore.R
import com.giraffe.media.explore.components.ExploreHeader
import com.giraffe.media.explore.components.HistoryAndRecentItems
import com.giraffe.media.explore.components.TransitionLazyColumnToGrid
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.util.toTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    ExploreContent(
        modifier = modifier,
        interactions = viewModel,
        state = state
    )
}

@Composable
fun ExploreContent(
    modifier: Modifier = Modifier,
    state: ExploreScreenState,
    interactions: ExploreInteractionListener
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Box {
        LazyColumn(
            modifier = modifier
                .background(Theme.color.background.screen)
                .statusBarsPadding()
                .padding(top = 16.dp),
            userScrollEnabled = !state.isSearchFieldFocused
        ) {
            stickyHeader {
                ExploreHeader(
                    modifier = Modifier.padding(bottom = 12.dp),
                    endIcon = painterResource(Theme.icons.outline.microphone),
                    onValueChange = interactions::onSearchQueryChange,
                    value = state.searchQuery,
                    placeholder = stringResource(R.string.search),
                    onFocusChanged = interactions::onFocusChanged,
                    onSearchClick = { focusManager.clearFocus() }
                )
            }
            if (!state.isSearchFieldFocused) stickyHeader {
                Tabs(
                    modifier = Modifier.background(Theme.color.background.screen),
                    titles = state.searchTabs.map { it.toTitle(context) },
                    selectedTabIndex = state.selectedTab.ordinal,
                    onTabSelected = interactions::onTabSelected
                )
            }
            item {
                if (!state.isSearchFieldFocused) {
                    ExploreItemsSection(
                        modifier = Modifier.fillParentMaxHeight(),
                        posters = state.selectedPosters,
                        selectedGenre = state.selectedGenre
                            ?: GenreUi(title = stringResource(R.string.all)),
                        genres = listOf(GenreUi(title = stringResource(R.string.all))) + state.selectedGenres,
                        isGridSelected = state.isGridSelected,
                        onGenreSelected = interactions::onGenreSelected
                    )
                } else {
                    HistoryAndRecentItems(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillParentMaxHeight(),
                        resultSearchKeyword = state.resultSearchKeyword,
                        recentViews = state.recentViews,
                        onClickClearAll = interactions::onClearHistory,
                        onClickItem = interactions::onSuggestionClick,
                        onClickIcon = {
                            if (it.isFromSearchHistory) {
                                interactions.onDeleteItemFromHistory(it)
                            } else {
                                interactions.onSearchQueryChange(it.keyword)
                            }
                        },
                    )
                }
            }
        }
        if (!state.isSearchFieldFocused) ViewToggle(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 16.dp, end = 16.dp),
            isListSelected = !state.isGridSelected,
            onGridSelected = interactions::onViewChanged,
        )
    }
}

@Composable
private fun ExploreItemsSection(
    modifier: Modifier = Modifier,
    posters: List<Poster>,
    selectedGenre: GenreUi,
    genres: List<GenreUi>,
    isGridSelected: Boolean,
    onGenreSelected: (GenreUi) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        CardsSection(
            posters = posters,
            isGridSelected = isGridSelected
        )
        GenresSection(
            genres = genres,
            selectedGenre = selectedGenre,
            onGenreSelected = onGenreSelected
        )
    }
}

@Composable
private fun GenresSection(
    modifier: Modifier = Modifier,
    selectedGenre: GenreUi,
    genres: List<GenreUi>,
    onGenreSelected: (GenreUi) -> Unit
) {
    LazyRow(
        modifier = modifier.padding(top = 12.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(genres) { genre ->
            Chip(
                text = genre.title,
                isSelected = genre == selectedGenre,
                onCheckedChange = { onGenreSelected(genre) }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CardsSection(posters: List<Poster>, isGridSelected: Boolean) {
    TransitionLazyColumnToGrid(
        poster = posters,
        isListSelected = !isGridSelected,
        contentPadding = PaddingValues(vertical = 60.dp)
    )
}


@Preview
@Composable
private fun Preview() {
    CineVerseTheme(isDarkTheme = true) {
        ExploreContent(
            state = ExploreScreenState(),
            interactions = object : ExploreInteractionListener {
                override fun onTextChange(text: String) {}

                override fun onSearchQueryChange(query: String) {}

                override fun onClearSearchQuery() {}

                override fun onDeleteItemFromHistory(item: SearchKeyword) {}

                override fun onClearHistory() {}

                override fun onVoiceSearchClick() {}

                override fun onClearRecentViewed() {}

                override fun onSuggestionClick(suggestion: SearchKeyword) {}

                override fun onTabSelected(tabIndex: Int) {}

                override fun onViewChanged(isGrid: Boolean) {}

                override fun onPermissionResult(granted: Boolean) {}

                override fun onVoiceSearchFinished() {}

                override fun onGenreSelected(genre: GenreUi) {}
                override fun onFocusChanged(isFocused: Boolean) {}
            }
        )
    }
}