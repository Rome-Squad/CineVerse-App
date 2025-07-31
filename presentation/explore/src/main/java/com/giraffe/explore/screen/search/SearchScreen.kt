package com.giraffe.explore.screen.search

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.giraffe.designsystem.composable.PosterListSection
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.SearchItem
import com.giraffe.explore.components.VoiceRecordingOverlay
import com.giraffe.explore.util.VoiceSearchHelper
import com.giraffe.media.explore.R

@Composable
fun SearchScreen(
    navigateToSearchResult: (String) -> Unit,
    onBackClick: () -> Unit,
    onClickPoster: (Poster) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current
    var rmsDbLevel by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getRecentViewedPoster()
        }
    }

    Box {
        SearchContent(
            state = state,
            interactions = viewModel,
            navigateToSearchResult = navigateToSearchResult,
            onBackClick = onBackClick,
            onClickPoster = onClickPoster,
            onRmsChanged = { rmsDbLevel = it }
        )
        VoiceRecordingOverlay(
            isRecording = state.isVoiceRecording && state.isPermissionGranted,
            rmsDbLevel = rmsDbLevel
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SearchContent(
    state: SearchScreenState,
    interactions: SearchInteractionListener,
    navigateToSearchResult: (String) -> Unit,
    onBackClick: () -> Unit,
    onClickPoster: (Poster) -> Unit,
    onRmsChanged: (Float) -> Unit
) {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        interactions.onPermissionResult(granted)
        val message = if (granted) {
            context.getString(R.string.voice_permission_granted)
        } else {
            context.getString(R.string.voice_permission_denied)
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    val voiceSearchHelper = remember {
        VoiceSearchHelper(
            context = context,
            onResult = { result ->
                if (result.isNotBlank()) {
                    interactions.onQueryChange(result)
                }
                interactions.onVoiceSearchFinished()
            },
            onRmsChanged = onRmsChanged,
            onError = { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                interactions.onVoiceSearchFinished()
            }
        )
    }

    val isKeyboardVisible = WindowInsets.isImeVisible

    LaunchedEffect(state.isVoiceRecording) {
        if (state.isVoiceRecording) {
            val isGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED

            if (isGranted) {
                interactions.onPermissionResult(true)
            } else {
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }
    LaunchedEffect(state.isVoiceRecording, state.isPermissionGranted) {
        if (state.isVoiceRecording && state.isPermissionGranted) {
            voiceSearchHelper.startListening()
        }
    }
    DisposableEffect(Unit) {
        onDispose { voiceSearchHelper.destroy() }
    }


    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = 16.dp),
    ) {
        stickyHeader {
            ExploreHeader(
                modifier = Modifier.padding(bottom = 12.dp),
                showBackButton = true,
                endIcon = painterResource(if (state.query.isBlank()) Theme.icons.outline.microphone else Theme.icons.outline.close),
                onValueChange = interactions::onQueryChange,
                value = state.query,
                placeholder = stringResource(R.string.search),
                focusRequester = focusRequester,
                onEndIconClick = interactions::onPostfixIconClick,
                onBackClick = onBackClick,
                onSearch = navigateToSearchResult
            )
        }

        keywordsSection(
            query = state.query,
            keywords = state.keywords,
            recentKeywords = state.recentKeywords,
            isKeyboardVisible = isKeyboardVisible,
            onClearClick = interactions::clearAllKeywords,
            onKeywordArrowClick = interactions::onQueryChange,
            onKeywordClearClick = interactions::deleteKeyword,
            onKeywordsClick = {
                interactions.onKeywordClick(it)
                navigateToSearchResult(it)
            }
        )

        item {
            PosterListSection(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                endText = stringResource(R.string.clear_all),
                title = stringResource(R.string.you_recent_viewed),
                posters = state.recentPosters,
                onClickEndText = interactions::clearAllRecentViewedPosters,
                onClickPoster = onClickPoster
            )
        }
    }
}

private fun LazyListScope.keywordsSection(
    query: String,
    keywords: List<String>,
    recentKeywords: List<String>,
    onClearClick: () -> Unit = {},
    onKeywordsClick: (String) -> Unit = {},
    onKeywordArrowClick: (String) -> Unit = {},
    onKeywordClearClick: (String) -> Unit = {},
    isKeyboardVisible: Boolean,
) {
    val isRecentKeyWordsVisible = recentKeywords.isNotEmpty()
    val isSearchResultKeywordsVisible = keywords.isNotEmpty()
    val isStickyHeaderVisible =
        isRecentKeyWordsVisible || isSearchResultKeywordsVisible || query.isNotBlank()

    if (isStickyHeaderVisible) {
        stickyHeader {
            SectionTitle(
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                title = if (isRecentKeyWordsVisible && !isSearchResultKeywordsVisible) stringResource(
                    R.string.history
                )
                else stringResource(R.string.search_suggestions),
                clickableText = if (isRecentKeyWordsVisible && !isSearchResultKeywordsVisible) stringResource(
                    R.string.clear_all
                ) else "",
                onClickableText = onClearClick
            )
        }
    }

    if (isRecentKeyWordsVisible) {
        items(items = recentKeywords, key = { it }) { keyWord ->
            SearchItem(
                text = keyWord,
                isRecent = true,
                postfixIcon = if (isKeyboardVisible) painterResource(Theme.icons.outline.arrowRightUp)
                else painterResource(Theme.icons.outline.close),
                onClickItem = onKeywordsClick,
                onClickIcon = {
                    if (isKeyboardVisible) onKeywordArrowClick(keyWord)
                    else onKeywordClearClick(keyWord)
                }
            )
        }
    }

    if (isSearchResultKeywordsVisible) {
        items(items = keywords, key = { it }) { keyWord ->
            SearchItem(
                text = keyWord,
                isRecent = false,
                postfixIcon = painterResource(Theme.icons.outline.arrowRightUp),
                onClickItem = onKeywordsClick,
                onClickIcon = { onKeywordArrowClick(keyWord) }
            )
        }
    }
}