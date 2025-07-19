package com.giraffe.explore.screen.discover

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.giraffe.designsystem.composable.Chip
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.TransitionLazyColumnToGrid
import com.giraffe.explore.navigation.route.navigateToSearch
import com.giraffe.explore.util.toTitle
import com.giraffe.media.explore.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun DiscoverScreen(
    navController: NavController,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    viewModel: DiscoverViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    ExploreContent(
        state,
        interactions = viewModel,
        navigateToSearch = navController::navigateToSearch,
        navigateToMovieDetails = navigateToMovieDetails,
        navigateToSeriesDetails = navigateToSeriesDetails
    )
}

@Composable
fun ExploreContent(
    state: DiscoverScreenState,
    interactions: DiscoverInteractionListener,
    navigateToSearch: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
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
                    placeholder = stringResource(R.string.search),
                    readOnly = true,
                    onTextFieldClicked = navigateToSearch
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
            item {
                GenresAndCardsSection(
                    modifier = Modifier.fillParentMaxHeight(),
                    posters = state.selectedPosters,
                    selectedGenre = state.selectedGenre
                        ?: GenreUi(title = stringResource(R.string.all)),
                    genres = listOf(GenreUi(title = stringResource(R.string.all))) + state.selectedGenres,
                    isGridSelected = state.isGridSelected,
                    onGenreSelected = interactions::onGenreSelected,
                    onClick = { posterId ->
                        when (state.selectedTab) {
                            SearchTab.MOVIES -> navigateToMovieDetails(posterId)
                            SearchTab.SERIES -> navigateToSeriesDetails(posterId)
                            SearchTab.ACTORS -> {}
                        }
                    }
                )
            }
        }
        ViewToggle(
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
private fun GenresAndCardsSection(
    modifier: Modifier = Modifier,
    posters: List<Poster>,
    selectedGenre: GenreUi,
    genres: List<GenreUi>,
    isGridSelected: Boolean,
    onGenreSelected: (GenreUi) -> Unit,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.screen)
    ) {
        TransitionLazyColumnToGrid(
            poster = posters,
            isListSelected = !isGridSelected,
            contentPadding = PaddingValues(vertical = 60.dp),
            onClick = onClick
        )
        GenresSection(
            modifier = Modifier.padding(top = 12.dp, bottom = 16.dp),
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
        modifier = modifier,
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