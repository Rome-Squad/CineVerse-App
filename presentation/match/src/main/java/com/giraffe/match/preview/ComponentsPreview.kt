package com.giraffe.match.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.match.R
import com.giraffe.match.components.HeroCarousel
import com.giraffe.match.components.ProgressIndicator
import com.giraffe.match.components.SelectionItem
import com.giraffe.match.model.SelectionType

@Preview
@Composable
private fun ProgressIndicatorPreviewLight() {
    CineVerseTheme(isDarkTheme = false) {
        Column {
            ProgressIndicator(
                progress = 0.25f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 0.5f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 0.75f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 1f,
            )
        }
    }
}
@Preview
@Composable
private fun ProgressIndicatorPreviewDark() {
    CineVerseTheme(isDarkTheme = true) {
        Column {
            ProgressIndicator(
                progress = 0.25f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 0.5f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 0.75f,
            )
            Spacer(Modifier.height(8.dp))
            ProgressIndicator(
                progress = 1f,
            )
        }
    }
}

@Preview
@Composable
fun SelectionItemPreviewWithoutIconLight() {
    CineVerseTheme(isDarkTheme = false) {
        SelectionItem(
            modifier = Modifier
                .size(
                    width = 69.dp,
                    height = 44.dp
                ),
            type = SelectionType.CHIP,
            isSelected = true,
            description = "Text",
            horizontalPadding = 20.dp,
            verticalPadding = 12.5.dp
        )
    }
}
@Preview
@Composable
fun SelectionItemPreviewWithoutIconDark() {
    CineVerseTheme(isDarkTheme = true) {
        SelectionItem(
            modifier = Modifier
                .size(
                    width = 69.dp,
                    height = 44.dp
                ),
            type = SelectionType.CHIP,
            isSelected = true,
            description = "Text",
            horizontalPadding = 20.dp,
            verticalPadding = 12.5.dp
        )
    }
}


@Preview
@Composable
private fun HeroCarouselLight() {
    val items: List<Int> = listOf(
        R.drawable.p1,
        R.drawable.p2,
        R.drawable.p3,
        R.drawable.p4,
        R.drawable.pp1
    )
    CineVerseTheme(isDarkTheme = false) {
        HeroCarousel(
            items = items, contentPadding = PaddingValues(horizontal = 40.dp)
        )
    }
}

@Preview
@Composable
private fun HeroCarouselDark() {
    val items: List<Int> = listOf(
        R.drawable.p1,
        R.drawable.p2,
        R.drawable.p3,
        R.drawable.p4,
        R.drawable.pp1
    )
    CineVerseTheme(isDarkTheme = true) {
        HeroCarousel(
            items = items, contentPadding = PaddingValues(horizontal = 40.dp)
        )
    }
}
