package com.giraffe.media.explore.screen

import android.Manifest
import android.util.Log
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
import androidx.compose.runtime.remember
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
import com.giraffe.explore.ExploreInteractionListener
import com.giraffe.explore.ExploreScreenState
import com.giraffe.explore.ExploreViewModel
import com.giraffe.explore.GenreUi
import com.giraffe.explore.SearchTab
import com.giraffe.media.explore.components.ExploreHeader
import com.giraffe.media.explore.components.NoResult
import com.giraffe.media.explore.components.ResultsActors
import com.giraffe.media.explore.components.TransitionLazyColumnToGrid
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.util.VoiceSearchHelper
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SearchScreenEffect.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                SearchScreenEffect.RefreshCompleted -> {
                    Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    SearchContent(
        modifier = modifier,
        state = state,
        listener = viewModel
    )
}

@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    state: ExploreScreenState,
    listener: ExploreInteractionListener
) {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        listener.onPermissionResult(granted)
        val message = if (granted) {
            context.getString(com.giraffe.media.explore.R.string.voice_permission_granted)
        } else {
            context.getString(com.giraffe.media.explore.R.string.voice_permission_denied)
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    val voiceSearchHelper = remember {
        VoiceSearchHelper(
            context = context,
            onResult = { result ->
                if (result.isNotBlank()) {
                    listener.onSearchQueryChange(result)
                    Toast.makeText(context, "You said: $result", Toast.LENGTH_SHORT).show()
                }
                listener.onVoiceSearchFinished()
            },
            onError = { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                listener.onVoiceSearchFinished()
            }
        )
    }

    LaunchedEffect(state.isVoiceRecording) {
        if (state.isVoiceRecording) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    LaunchedEffect(state.isVoiceRecording, state.isPermissionGranted) {
        if (state.isVoiceRecording && state.isPermissionGranted) {
            Log.v("VoiceSearch", "Voice recording started")
            voiceSearchHelper.startListening()
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
                modifier = Modifier.padding(horizontal = 16.dp),
                showBackButton = true,
                endIcon = painterResource(Theme.icons.outline.microphone),
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
                    listener.onTabSelected(selectedTab.ordinal)
                },
                onValueChange = { query ->
                    listener.onSearchQueryChange(query)
                },
                value = state.searchQuery,
                onEndIconClick = {
                    listener.onVoiceSearchClick()
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    state.isLoading -> Progress()


                    else -> when (state.selectedTab) {
                        SearchTab.MOVIES, SearchTab.SERIES -> {
                            if (state.movieResults.isEmpty()) NoResult()
                            else TransitionLazyColumnToGrid(
                                poster = state.movieResults,
                                isListSelected = !state.isGridSelected
                            )
                        }

                        SearchTab.ACTORS -> {
                            if (state.actorResults.isEmpty()) NoResult()
                            else ResultsActors(state.actorResults)
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
                onGridSelected = {},
            )
        }
    }
}

@Preview()
@Composable
private fun ExploreScreenPreview() {
    CineVerseTheme {
        SearchContent(
            state = ExploreScreenState(),
            listener = object : ExploreInteractionListener {
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