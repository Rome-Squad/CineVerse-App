package com.giraffe.designsystem.composable


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Icon
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
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = icon,
                contentDescription = stringResource(R.string.card_icon),
                tint = Theme.color.brand.primary,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Theme.color.shade.quinary,
                        shape = RoundedCornerShape(Theme.radius.full)
                    )
                    .padding(12.dp)
            )
            InfoSection(
                title = title,
                description = caption,
                descriptionLimits = 2,
                spaceBetween = 4
            )
        }

        Icon(
            painter = painterResource(Theme.icons.outline.altArrowRight),
            contentDescription = stringResource(R.string.alt_arrow_right_icon),
            tint = Theme.color.shade.tertiary,
            modifier = Modifier
                .size(20.dp)
                .graphicsLayer {
                    scaleX = if (isRtl) -1f else 1f
                }
        )
    }
}

@CinePreview
@Composable
private fun Preview() {
    CineVerseTheme {
        CTACard(
            title = "Browse Everything",
            caption = "Explore all movies and series by genre or search for anything you like.",
            icon = painterResource(Theme.icons.dueTone.magicStick),
            modifier = Modifier.fillMaxWidth()
        ) { }
    }
}