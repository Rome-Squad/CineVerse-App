package com.giraffe.designsystem.composable

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun ViewToggle(
    isListSelected: Boolean,
    modifier: Modifier = Modifier,
    onGridSelected: (Boolean) -> Unit = {},
) {
    val gridBackgroundColor by animateColorAsState(
        targetValue = if (isListSelected) {
            Theme.color.shade.secondary
        } else {
            Theme.color.brand.primary
        }
    )

    val listBackgroundColor by animateColorAsState(
        targetValue = if (isListSelected) {
            Theme.color.brand.primary
        } else {
            Theme.color.shade.secondary
        }
    )

    val alignment by animateDpAsState(
        targetValue = if (isListSelected) 40.dp else 0.dp,
    )

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .background(Theme.color.background.card, RoundedCornerShape(Theme.radius.s))
                .border(1.dp, Theme.color.stroke.primary, RoundedCornerShape(Theme.radius.s))
        )

        Box(
            modifier = Modifier
                .offset(x = alignment)
                .size(40.dp)
                .background(Theme.color.brand.tertiary, RoundedCornerShape(Theme.radius.s))
                .border(1.dp, Theme.color.brand.secondary, RoundedCornerShape(Theme.radius.s))
        )

        ToggleButtonIcon(
            onClick = { onGridSelected(true) },
            isSelected = isListSelected,
            iconColor = gridBackgroundColor,
            selectedIconResId = Theme.icons.outline.grid,
            unselectedIconResId = Theme.icons.dueTone.grid,
            modifier = Modifier.align(Alignment.CenterStart),
        )

        ToggleButtonIcon(
            onClick = { onGridSelected(false) },
            isSelected = isListSelected,
            iconColor = listBackgroundColor,
            selectedIconResId = Theme.icons.dueTone.rowVertical,
            unselectedIconResId = Theme.icons.outline.rowVertical,
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}


@Composable
private fun ToggleButtonIcon(
    isSelected: Boolean,
    iconColor: Color,
    selectedIconResId: Int,
    unselectedIconResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.s))
            .clickable(onClick = onClick)
            .size(40.dp)
            .wrapContentSize()
            .padding(horizontal = 10.dp),
        targetState = isSelected,
        label = "FavoriteIcon"
    ) { selected ->
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = if (selected)
                painterResource(selectedIconResId)
            else
                painterResource(unselectedIconResId),
            contentDescription = "Grid icon for View Toggle",
            tint = iconColor
        )
    }
}

@Preview
@Composable
private fun ViewTogglePreview() {
    var isListSelected by remember { mutableStateOf(false) }
    CineVerseTheme(isDarkTheme = true) {
        ViewToggle(
            isListSelected = isListSelected,
            onGridSelected = { isListSelected = !isListSelected },
        )
    }
}