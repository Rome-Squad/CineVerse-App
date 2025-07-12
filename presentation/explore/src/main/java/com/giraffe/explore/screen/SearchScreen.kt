package com.giraffe.explore.screen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.composable.ViewToggle
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.explore.VoiceSearchHelper
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.HistoryAndRecentItems
import com.giraffe.explore.components.NoResult
import com.giraffe.explore.components.ResultsActors
import com.giraffe.explore.components.TransitionLazyColumnToGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    SearchContent(modifier = modifier, state = state, onIntent = viewModel::onIntent)
}

@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    state: SearchScreenState,
    onIntent: (SearchIntent) -> Unit
) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
        val message = if (granted) {
            context.getString(com.giraffe.explore.R.string.voice_permission_granted)
        } else {
            context.getString(com.giraffe.explore.R.string.voice_permission_denied)
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    val voiceSearchHelper = remember {
        VoiceSearchHelper(
            context = context,
            onResult = { result ->
                if (result.isNotBlank()) {
                    onIntent(SearchIntent.OnSearchQueryChange(result))
                    Toast.makeText(context, "You said: $result", Toast.LENGTH_SHORT).show()
                }
            },
            onError = { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        )
    }

    LaunchedEffect(state.isVoiceRecording) {
        if (state.isVoiceRecording) {
            if (permissionGranted) {
                voiceSearchHelper.startListening()
            } else {
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { voiceSearchHelper.destroy() }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Theme.color.background.screen)
    ) {
        Column {
            ExploreHeader(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
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
                    val selectedTab = when (index) {
                        0 -> SearchTab.MOVIES
                        1 -> SearchTab.SERIES
                        2 -> SearchTab.ACTORS
                        else -> SearchTab.MOVIES
                    }
                    onIntent(SearchIntent.OnSelectedTabChanged(selectedTab))
                },
                onValueChange = { query -> onIntent(SearchIntent.OnSearchQueryChange(query)) },
                value = state.searchQuery,
                onEndIconClick = { onIntent(SearchIntent.OnVoiceSearchClick) }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    state.isLoading -> Progress()

                    !state.isSearchResultsVisible -> HistoryAndRecentItems(
                        state = state,
                        onClickClearAll = {
                            onIntent(SearchIntent.OnClearHistory)
                        },
                        onClickItem = {
                            onIntent(SearchIntent.OnClickItem(it))
                        },
                        onClickIcon = {
                            if (it.isFromSearchHistory) onIntent(SearchIntent.OnDeleteItemHistory(it))
                            else
                                onIntent(SearchIntent.OnSearchQueryChange(it.keyword))
                        },
                    )

                    else -> when (state.selectedTab) {
                        SearchTab.MOVIES -> {
                            if (state.mediaResults.isEmpty()) NoResult()
                            TransitionLazyColumnToGrid(
                                poster = state.mediaResults,
                                isListSelected = !state.isGridSelected
                            )
                        }

                        SearchTab.SERIES -> {
                            if (state.mediaResults.isEmpty()) NoResult()
                            TransitionLazyColumnToGrid(
                                poster = state.mediaResults,
                                isListSelected = !state.isGridSelected
                            )
                        }

                        SearchTab.ACTORS -> {
                            if (state.mediaResults.isEmpty()) NoResult()
                            else ResultsActors(state.mediaResults)
                        }
                    }
                }
            }
        }

        if (state.isSearchResultsVisible &&
            (state.selectedTab == SearchTab.MOVIES || state.selectedTab == SearchTab.SERIES)
        ) {
            ViewToggle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                isListSelected = !state.isGridSelected,
                onViewToggle = { onIntent(SearchIntent.onClickToggleView) },
            )
        }
    }
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