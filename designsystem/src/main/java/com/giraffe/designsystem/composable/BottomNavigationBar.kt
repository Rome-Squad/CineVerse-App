package com.giraffe.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

enum class BottomTab {
    Home, Explore, Match, Me
}

@Composable
fun BottomNavigationBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(Theme.color.background.card)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Theme.color.stroke.primary)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BottomTab.entries.forEach { tab ->
                BuildingBlock(
                    icon = tab.getPainterForTab(),
                    label = tab.getLabelForTab(),
                    isSelected = selectedTab == tab,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(tab) }
                        .padding(vertical = 13.dp)
                )
            }
        }
    }

}

@Composable
private fun BottomTab.getLabelForTab(): String = when (this) {
    BottomTab.Home -> stringResource(R.string.home)
    BottomTab.Explore -> stringResource(R.string.explore)
    BottomTab.Match -> stringResource(R.string.match)
    BottomTab.Me -> stringResource(R.string.me)
}

@Composable
private fun BottomTab.getPainterForTab(): Painter = when (this) {
    BottomTab.Home -> painterResource(Theme.icons.outline.home)
    BottomTab.Explore -> painterResource(Theme.icons.outline.search)
    BottomTab.Match -> painterResource(Theme.icons.outline.magicStick)
    BottomTab.Me -> painterResource(Theme.icons.outline.userSquare)
}

@PreviewLightDark
@Composable
fun BottomNavigationBarPreview() {
    var selectedTab by rememberSaveable { mutableStateOf(BottomTab.Home) }
    CineVerseTheme {
        BottomNavigationBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
    }
}