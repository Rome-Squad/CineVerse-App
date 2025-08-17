package com.giraffe.presentation.explore.screen.search

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giraffe.designsystem.composable.NoInternetScreen
import com.giraffe.designsystem.composable.Progress
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.explore.R
import com.giraffe.presentation.explore.components.ExploreHeader
import com.giraffe.presentation.explore.components.PosterListSection
import com.giraffe.presentation.explore.screen.search.components.VoiceRecordingOverlay
import com.giraffe.presentation.explore.screen.search.components.keywordsSection
import com.giraffe.presentation.explore.util.EffectListener
import com.giraffe.presentation.explore.util.VoiceSearchHelper
import com.giraffe.presentation.explore.util.mapExceptionToStringRes
import com.giraffe.presentation.explore.util.showToast

@Composable
fun SearchScreen(
    navigateToSearchResult: (String) -> Unit,
    onBackClick: () -> Unit,
    navigateToMovieDetails: (Int) -> Unit,
    navigateToSeriesDetails: (Int) -> Unit,
    navigateToPersonDetails: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var rmsDbLevel by remember { mutableFloatStateOf(0f) }
    val context = LocalContext.current

    EffectListener(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            is SearchEffect.ShowError -> context.showToast(mapExceptionToStringRes(effect.error))
            is SearchEffect.NavigateBack -> onBackClick()
            is SearchEffect.NavigateToMovieDetail -> navigateToMovieDetails(effect.movieId)
            is SearchEffect.NavigateToPersonDetails -> navigateToPersonDetails(effect.personId)
            is SearchEffect.NavigateToSeriesDetail -> navigateToSeriesDetails(effect.seriesId)
            is SearchEffect.NavigateToSearchResult -> navigateToSearchResult(effect.result)
        }
    }

    Box {
        SearchContent(
            state = state,
            interactions = viewModel,
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

    if (!state.isLoading && !state.isNoInternet) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    if (state.isNoInternet) NoInternetScreen(onRetryClick = interactions::onRetryClick)
    else {
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
                    onBackClick = interactions::onBackClick,
                    onSearch = interactions::onSearchClick
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
                    interactions.onSearchClick(it)
                }
            )

            item {
                if (state.isLoading) Box(
                    Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Progress() }
                else {
                    PosterListSection(
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                        endText = stringResource(R.string.clear_all),
                        title = stringResource(R.string.you_recent_viewed_history),
                        posters = state.recentPosters,
                        onClickEndText = interactions::clearAllRecentViewedPosters,
                        onClickPoster = interactions::onClickPoster
                    )
                }
            }
        }
    }
}