package com.giraffe.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.composable.AppTextField
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun ExploreHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSearchClick: (String) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current


    val endIcon: @Composable (() -> Unit) = {
        if (isFocused) {
            EndIcon(
                icon = painterResource(Theme.icons.outline.close),
                onClick = {
                    focusManager.clearFocus()
                },
                contentDescription = "Cancel Search"
            )
        } else {
            EndIcon(
                icon = painterResource(Theme.icons.outline.microphone),
                onClick = { },
                contentDescription = "Microphone"
            )
        }
    }

    Column(
        modifier = modifier
            .background(color = Theme.color.background.screen),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isFocused) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(20.dp)
                        .clickable(onClick = onBackClick),
                    painter = painterResource(Theme.icons.outline.arrowLeft),
                    contentDescription = "Back",
                    tint = Theme.color.shade.primary
                )
            }
            AppTextField(
                modifier = modifier,
                startIcon = painterResource(Theme.icons.outline.search),
                onClickStartIcon = onSearchClick,
                endIcon = endIcon,
//                placeholder = "Search..."
            )
        }
        //  Add Tabs
    }

}


@Composable
fun EndIcon(
    modifier: Modifier = Modifier,
    icon: Painter,
    onClick: () -> Unit = {},
    contentDescription: String? = null
) {
    Icon(
        modifier = modifier
            .clickable(
                onClick = onClick
            ),
        painter = icon,
        contentDescription = contentDescription,
        tint = Theme.color.shade.primary
    )
}

@Preview
@Composable
fun ExploreHeaderPreview() {
    CineVerseTheme(isDarkTheme = true) {
        ExploreHeader(
            onBackClick = {},
        )
    }
}