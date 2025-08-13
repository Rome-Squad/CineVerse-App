package com.giraffe.presentation.explore.screen.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.SectionTitle
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.explore.R

fun LazyListScope.keywordsSection(
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
                postfixIcon = if (query.isNotEmpty()) painterResource(Theme.icons.outline.arrowLeftUp)
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
                postfixIcon = painterResource(Theme.icons.outline.arrowLeftUp),
                onClickItem = onKeywordsClick,
                onClickIcon = { onKeywordArrowClick(keyWord) }
            )
        }
    }
}