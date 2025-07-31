package com.giraffe.explore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.DefaultTextField
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.media.explore.R

@Composable
fun ExploreHeader(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    endIcon: Painter,
    onEndIconClick: () -> Unit = {},
    tabsTitles: List<String> = listOf(),
    onTabClick: (Int) -> Unit = {},
    selectedTabIndex: Int = 0,
    placeholder: String = "",
    value: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    focusRequester: FocusRequester = FocusRequester(),
    onSearch: (String) -> Unit = {},
    onTextFieldClicked: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier.background(color = Theme.color.background.screen),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showBackButton) {
                Image(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .padding(start = 10.dp)
                        .size(20.dp)
                        .clickable(onClick = onBackClick),
                    painter = painterResource(Theme.icons.outline.arrowLeft),
                    contentDescription = stringResource(R.string.back),
                    colorFilter = ColorFilter.tint(Theme.color.shade.primary)
                )
            }
            DefaultTextField(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp
                    ),
                singleLine = true,
                maxCharacters = 400,
                startIcon = painterResource(Theme.icons.outline.search),
                endIcon = {
                    Image(
                        modifier = Modifier.clickable(onClick = onEndIconClick),
                        painter = endIcon,
                        contentDescription = "end_icon",
                        colorFilter = ColorFilter.tint(Theme.color.shade.primary)
                    )
                },
                placeholder = placeholder,
                onValueChange = onValueChange,
                value = value,
                onFocusChanged = onFocusChanged,
                enabled = enabled,
                readOnly = readOnly,
                focusRequester = focusRequester,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch(value) }),
                onClick = onTextFieldClicked
            )
        }
        if (tabsTitles.isNotEmpty()) {
            Tabs(
                titles = tabsTitles,
                onTabSelected = onTabClick,
                selectedTabIndex = selectedTabIndex,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val (searchText, onSearchTextChange) = remember { mutableStateOf("") }
    CineVerseTheme(isDarkTheme = true) {
        ExploreHeader(
            endIcon = painterResource(Theme.icons.outline.microphone),
            value = searchText,
            onValueChange = onSearchTextChange,
            placeholder = "search"
        )
    }
}
