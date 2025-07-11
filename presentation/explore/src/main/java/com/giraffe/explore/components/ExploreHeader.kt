package com.giraffe.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Tabs
import com.giraffe.designsystem.composable.TextField
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun ExploreHeader(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onSearchClick: (String) -> Unit = {},
    endIcon: Painter,
    onEndIconClick: () -> Unit = {},
    viewTaps: Boolean = false,
    tabsTitles: List<String> = listOf(""),
    onTabClick: (Int) -> Unit = {},
    selectedTabIndex: Int = 0,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .background(color = Theme.color.background.screen)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(20.dp)
                        .clickable(onClick = onBackClick)
                        .graphicsLayer {
                            rotationY = if (isRtl) 180f else 0f
                        },
                    painter = painterResource(Theme.icons.outline.arrowLeft),
                    contentDescription = "Back",
                    tint = Theme.color.shade.primary
                )

            }
            TextField(
                modifier = Modifier,
                startIcon = painterResource(Theme.icons.outline.search),
                onClickStartIcon = onSearchClick,
                endIcon = {
                    EndIcon(
                        icon = endIcon,
                        onClick = onEndIconClick,
                        contentDescription = "End Icon"
                    )
                },
                placeholder = "Search...",
                onValueChange = onValueChange,
                value = value,
            )
        }
        if (viewTaps) {
            Tabs(
                titles = tabsTitles,
                onTabSelected = onTabClick,
                selectedTabIndex = selectedTabIndex,
            )
        }
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
    CineVerseTheme(isDarkTheme = false) {
        ExploreHeader(
            onBackClick = {},
            showBackButton = true,
            endIcon = painterResource(Theme.icons.outline.microphone),
            viewTaps = false,
//            tabsTitles = listOf("Movie", "Series"),
//            onTabClick = {},
//            selectedTabIndex = 0,
            onValueChange = {},
            value = ""
        )
    }
}
