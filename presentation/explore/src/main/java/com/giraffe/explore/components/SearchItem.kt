package com.giraffe.explore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    text: String,
    isFromHistory: Boolean,
    onClick: () -> Unit
) {
    val iconId = if (isFromHistory) Theme.icons.outline.history else Theme.icons.outline.search

    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )


        Text(
            text = text,
            style = Theme.textStyle.body.md.medium,
            color = Theme.color.shade.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )


        Image(
            painter = painterResource(id = Theme.icons.outline.arrowRightUp),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .graphicsLayer {
                    rotationY = 180f
                }
        )
    }
    Spacer(
        modifier = modifier
            .height(1.dp)
            .background(Theme.color.shade.quaternary)
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF121321)
@Composable
fun SearchItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        Column {
            SearchItem( text = "Batman", isFromHistory = true, onClick = {})
            SearchItem(text = "The Batman", isFromHistory = false, onClick = {})
        }
    }
}
