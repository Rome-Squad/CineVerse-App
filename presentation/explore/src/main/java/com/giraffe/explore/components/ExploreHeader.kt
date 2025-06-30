package com.giraffe.explore.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.designsystem.theme.CineVerseTheme

@Composable
fun ExploreHeader(
    modifier: Modifier = Modifier,
    title: String = "Explore",
    onBackClick: () -> Unit = {},
) {
    Text(title)
}

@Preview
@Composable
fun ExploreHeaderPreview() {
    CineVerseTheme {
        ExploreHeader(
            title = "Explore",
            onBackClick = {}
        )
    }
}