package com.giraffe.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.PosterMovie
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.HistoryAndRecentItems
import com.giraffe.explore.components.NoResult
import com.giraffe.explore.components.ResultsActors
import com.giraffe.explore.components.ResultsMoviesOrSeriousGrid
import com.giraffe.explore.components.ResultsMoviesOrSeriousList
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    SearchContent(state = state, onIntent = viewModel::onIntents)
}

@Composable
fun SearchContent(
    state: SearchScreenState,
    onIntent: (SearchIntent) -> Unit
) {
    //using view model
    var isListSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
    ) {
        Column() {
            ExploreHeader(
                showBackButton = true,
                endIcon = painterResource(Theme.icons.outline.microphone),
                viewTaps = state.isSearchResultsVisible,
                tabsTitles = listOf(
                    stringResource(R.string.movies),
                    stringResource(R.string.series),
                    stringResource(R.string.actors)
                ),
                selectedTabIndex = state.selectedTab.ordinal,
                onTabClick = { index ->
                    //move to view model
                    val selectedTab = when (index) {
                        0 -> SearchTab.MOVIES
                        1 -> SearchTab.SERIES
                        2 -> SearchTab.ACTORS
                        else -> SearchTab.MOVIES
                    }
                    onIntent(SearchIntent.OnSelectedTabChanged(selectedTab))
                },
                onValueChange = { query -> onIntent(SearchIntent.OnSearchQueryChange(query)) },
                value = state.searchQuery
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Progress()
                    }
                } else if (!state.isSearchResultsVisible) {
                    HistoryAndRecentItems(
                        state = state,
                        onIntent = onIntent
                    )
                } else {
                    when (state.selectedTab) {
                        SearchTab.MOVIES -> {
                            if (state.movieResults.isEmpty()) {
                                NoResult()
                            } else {
                                if (isListSelected) {
                                    ResultsMoviesOrSeriousList(media = state.movieResults)
                                } else {
                                    ResultsMoviesOrSeriousGrid(
                                        media = state.movieResults,
                                    )
                                }
                            }
                        }

                        SearchTab.SERIES -> {
                            if (state.seriesResults.isEmpty()) {
                                NoResult()
                            } else {
                                if (isListSelected) {
                                    ResultsMoviesOrSeriousList(media = state.seriesResults)
                                } else {
                                    ResultsMoviesOrSeriousGrid(
                                        media = state.seriesResults,
                                    )
                                }
                            }
                        }

                        SearchTab.ACTORS -> {
                            if (state.actorResults.isEmpty()) {
                                NoResult()
                            } else {
                                ResultsActors(state.actorResults)
                            }
                        }
                    }
                }
            }
        }
        if (state.isSearchResultsVisible && (state.selectedTab == SearchTab.MOVIES || state.selectedTab == SearchTab.SERIES)) {
            ViewToggle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                isListSelected = isListSelected,
                onViewToggle = { newValue -> isListSelected = newValue },
            )
        }
    }
}

fun MediaStateUi.toPosterMovie(): PosterMovie {
    return PosterMovie(
        title = title,
        imageUri = imageUrl,
        rating = rate.toFloat(),
        genres = genresId.toString(),
        date = releaseDate.toString(),
        time = duration
    )
}

@Preview()
@Composable
private fun ExploreScreenPreview() {
    CineVerseTheme {
        SearchContent(
            state = SearchScreenState(),
            onIntent = {}
        )
    }
}