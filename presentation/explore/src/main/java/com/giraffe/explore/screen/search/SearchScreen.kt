package com.giraffe.explore.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.giraffe.designsystem.composable.MoviesListSection
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.explore.components.ExploreHeader
import com.giraffe.explore.components.SearchItem
import com.giraffe.explore.nav.route.navigateToSearchResult
import com.giraffe.media.explore.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    SearchContent(
        state,
        viewModel,
        navController::navigateToSearchResult,
        navController::popBackStack
    )
}

@Composable
fun SearchContent(
    state: SearchScreenState,
    interactions: SearchInteractionListener,
    navigateToSearchResult: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(state.isSearchFieldFocused) {
        if (!state.isSearchFieldFocused) focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
            .statusBarsPadding()
            .padding(top = 16.dp),
    ) {
        ExploreHeader(
            modifier = Modifier.padding(bottom = 12.dp),
            showBackButton = true,
            endIcon = painterResource(if (state.query.isBlank()) Theme.icons.outline.microphone else Theme.icons.outline.close),
            onValueChange = interactions::onQueryChange,
            value = state.query,
            placeholder = stringResource(R.string.search),
            focusRequester = focusRequester,
            onEndIconClick = interactions::onPostfixIconClick,
            onBackClick = onBackClick
        )
        if ((state.recentKeywords + state.keywords).isNotEmpty()) {
            KeywordsSection(
                modifier = Modifier.weight(1f),
                query = state.query,
                keywords = state.keywords,
                recentKeywords = state.recentKeywords,
                onClearClick = interactions::clearAllKeywords,
                onKeywordArrowClick = interactions::onQueryChange,
                onKeywordClearClick = interactions::deleteKeyword,
                onKeywordsClick = {
                    interactions.onKeywordClick(it)
                    navigateToSearchResult(it)
                }
            )
        }

        MoviesListSection(
            modifier = Modifier.padding(top = 16.dp),
            endText = stringResource(R.string.clear_all),
            title = stringResource(R.string.you_recent_viewed),
            movies = state.recentPosters
        )
    }
}

@Composable
fun KeywordsSection(
    modifier: Modifier = Modifier,
    query: String,
    keywords: List<String>,
    recentKeywords: List<String>,
    onClearClick: () -> Unit = {},
    onKeywordsClick: (String) -> Unit = {},
    onKeywordArrowClick: (String) -> Unit = {},
    onKeywordClearClick: (String) -> Unit = {},
) {
    val isJustRecent = keywords.isEmpty() && recentKeywords.isNotEmpty()
    LazyColumn(
        modifier = modifier
    ) {
        stickyHeader {
            SectionTitle(
                modifier = Modifier
                    .background(Theme.color.background.screen)
                    .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                title = if (isJustRecent) stringResource(R.string.history) else stringResource(R.string.search_suggestions),
                clickableText = if (isJustRecent) stringResource(R.string.clear_all) else "",
                onClickableText = onClearClick
            )
        }
        items(recentKeywords) { keyWord ->
            SearchItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = keyWord,
                isRecent = isJustRecent,
                postfixIcon = if (query.isBlank()) painterResource(Theme.icons.outline.close) else painterResource(
                    Theme.icons.outline.arrowRightUp
                ),
                onClickItem = onKeywordsClick,
                onClickIcon = {
                    if (isJustRecent) onKeywordClearClick(keyWord) else onKeywordArrowClick(
                        keyWord
                    )
                },
            )
        }
        items(keywords) { keyWord ->
            SearchItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = keyWord,
                isRecent = false,
                postfixIcon = if (query.isBlank()) painterResource(Theme.icons.outline.close) else painterResource(
                    Theme.icons.outline.arrowRightUp
                ),
                onClickItem = onKeywordsClick,
                onClickIcon = { onKeywordArrowClick(keyWord) },
            )
        }
    }
}