package com.giraffe.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tabs(
    titles: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(selectedTabIndex, matchContentSize = false)
                    .padding(horizontal = 16.dp),
                height = 2.dp,
                width = Dp.Unspecified,
                shape = RoundedCornerShape(
                    topStart = Theme.radius.full,
                    topEnd = Theme.radius.full
                ),
                color = Theme.color.brand.primary
            )
        },
        divider = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Theme.color.stroke.primary)
            )
        },
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        color = if (selectedTabIndex == index) Theme.color.brand.primary else Theme.color.shade.tertiary,
                        style = Theme.textStyle.body.md.medium,
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F7F7)
@Composable
fun CustomTabsPreview() {
    CineVerseTheme(isDarkTheme = false) {
        var selectedIndex by remember { mutableIntStateOf(0) }
        val tabTitles = listOf("Home", "Profile", "Settings")

        Tabs(
            titles = tabTitles,
            selectedTabIndex = selectedIndex,
            onTabSelected = { selectedIndex = it }
        )
    }
}