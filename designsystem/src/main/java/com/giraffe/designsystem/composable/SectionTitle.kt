package com.giraffe.designsystem.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    clickableText: String? = null,
    onClickableText: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
            maxLines = 1
        )
        clickableText?.let {
            Text(
                text = clickableText,
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.brand.primary,
                modifier = Modifier.clickable(onClick = onClickableText)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SectionTitlePreview() {
    CineVerseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionTitle(
                title = "Movies",
                clickableText = "ShowMore",
                onClickableText = {}
            )
            SectionTitle(
                title = "Movies",
            )
        }
    }
}