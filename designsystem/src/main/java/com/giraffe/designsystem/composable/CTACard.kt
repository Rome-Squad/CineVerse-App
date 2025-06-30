package com.giraffe.designsystem.composable


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun CTACard(
    title: String,
    caption: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Theme.color.shade.quinary, shape = RoundedCornerShape(Theme.radius.full)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Theme.color.brand.primary,
                modifier = Modifier.size(24.dp)
            )
        }
        InfoSection(
            title = title,
            description = caption,
            modifier = Modifier
                .weight(1f),
            descriptionLimits = 2
        )
        Icon(
            painter = painterResource(Theme.icons.outline.altArrowRight),
            contentDescription = null,
            tint = Theme.color.shade.tertiary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
fun PreviewCTACard() {
    CineVerseTheme {
        CTACard(
            title = "Browse Everything",
            caption = "Explore all movies and series by genre or search for anything you like.",
            icon = painterResource(Theme.icons.dueTone.magicStick),
        ) { }
    }
}