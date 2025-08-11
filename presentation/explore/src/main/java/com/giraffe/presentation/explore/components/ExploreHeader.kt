package com.giraffe.presentation.explore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.DefaultTextField
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.explore.R

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
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showBackButton) {
                Image(
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 4.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onBackClick)
                        .padding(10.dp),
                    painter = painterResource(Theme.icons.outline.arrowLeft),
                    contentDescription = stringResource(R.string.back),
                    colorFilter = ColorFilter.tint(Theme.color.shade.primary)
                )
            }
            DefaultTextField(
                modifier = Modifier.padding(
                    start = if (showBackButton) 0.dp else 16.dp,
                    end = 16.dp
                ),
                singleLine = true,
                maxCharacters = 400,
                startIcon = painterResource(Theme.icons.outline.search),
                endIcon = {
                    Image(
                        painter = endIcon,
                        contentDescription = stringResource(R.string.end_icon),
                        colorFilter = ColorFilter.tint(Theme.color.shade.primary),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onEndIconClick)
                            .padding(10.dp)
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