package com.giraffe.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun InfoCard(
    description: String,
    modifier: Modifier = Modifier,
    onClosedClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.s))
            .background(Theme.color.background.card)
            .border(1.dp, Theme.color.stroke.primary, RoundedCornerShape(Theme.radius.s))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Theme.icons.dueTone.infoCircle),
            contentDescription = null,
            tint = Theme.color.brand.primary,
            modifier = Modifier
                .size(20.dp)
        )
        Text(
            text = description,
            style = Theme.textStyle.body.sm.regular,
            color = Theme.color.shade.primary,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            painter = painterResource(Theme.icons.outline.close),
            contentDescription = null,
            tint = Theme.color.shade.secondary,
            modifier = Modifier
                .size(16.dp)
                .clickable(onClick = onClosedClick)
        )

    }
}

@Preview
@Composable
fun PreviewInfoCard() {
    CineVerseTheme {
        InfoCard(
            description = "Tip: Swipe left to remove movies from your history.",
            modifier = Modifier.width(328.dp)
        )
    }
}