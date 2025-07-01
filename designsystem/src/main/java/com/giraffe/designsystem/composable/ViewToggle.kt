package com.giraffe.designsystem.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun ViewToggle(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    val enabledGridList = onCheckedChange != null

    val defaultGridBackgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            Theme.color.brand.primary
        } else {
            Theme.color.shade.secondary
        }
    )

    val alignment by animateDpAsState(
        targetValue = if (isSelected) 40.dp else 0.dp,
    )

    Box(
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(Theme.radius.s))
            .background(Theme.color.background.card)
            .border(1.dp, Theme.color.stroke.primary, RoundedCornerShape(Theme.radius.s))
            .clickable(
                enabled = enabledGridList, onClick = {
                    onCheckedChange?.invoke(!isSelected)
                }
            )
    ) {

        Box(
            modifier = Modifier
                .offset(x = alignment)
                .size(40.dp)
                .clip(RoundedCornerShape(Theme.radius.s))
                .background(Theme.color.brand.tertiary)
                .border(1.dp, Theme.color.brand.secondary, RoundedCornerShape(Theme.radius.s))
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 10.dp)
                .size(20.dp),
            painter = if (enabledGridList)
                painterResource(Theme.icons.outline.grid)
            else
                painterResource(Theme.icons.dueTone.grid),
            contentDescription = "Grid icon for View Toggle",
            tint = defaultGridBackgroundColor
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 10.dp)
                .size(20.dp),
            painter = painterResource(Theme.icons.outline.rowVertical),
            contentDescription = "Grid icon for View Toggle",
            tint = Theme.color.shade.secondary
        )

    }

}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFF7F7F7)
@Composable
private fun ViewTogglePreview() {
    var selectedChip by remember { mutableStateOf(false) }
    CineVerseTheme(isDarkTheme = true) {
        ViewToggle(
            modifier = Modifier.padding(40.dp),
            isSelected = selectedChip,
            onCheckedChange = { selectedChip = !selectedChip },
        )
    }
}