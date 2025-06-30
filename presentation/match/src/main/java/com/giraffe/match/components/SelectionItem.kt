package com.giraffe.match.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


enum class SelectionType {
    CARD,
    CHIP
}

@Composable
fun SelectionItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    type: SelectionType = SelectionType.CARD,
    icon: Painter = painterResource(Theme.icons.dueTone.headphone),
    description: String,
    onClick: () -> Unit = {}
) {
    var backgroundColor = Theme.color.background.card
    var iconBackgroundColor = Theme.color.brand.tertiary
    var textColor = Theme.color.shade.primary
    if (isSelected) {
        backgroundColor = Theme.color.brand.tertiary
        iconBackgroundColor = Theme.color.brand.secondary
        textColor = Theme.color.brand.primary
    }
    val borderColor = if (isSelected) Theme.color.brand.secondary else Color.Transparent

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(Theme.radius.lg),
                color = borderColor
            )
            .clip(
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .clickable(onClick = onClick)
            .size(
                width = if (type == SelectionType.CARD) 328.dp else 69.dp,
                height = if (type == SelectionType.CARD) 56.dp else 44.dp,
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = if (type == SelectionType.CHIP) 20.dp else 12.dp,
                    vertical = if (type == SelectionType.CHIP) 12.5.dp else 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (type == SelectionType.CARD) {
                SelectionIcon(
                    modifier = Modifier.padding(end = 12.dp),
                    icon = icon,
                    backgroundColor = iconBackgroundColor
                )
            }
            Text(
                color = textColor,
                text = description,
                style = Theme.textStyle.body.md.medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        SelectionItem(description = "Text")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionItemPreviewIsSelected() {
    CineVerseTheme(isDarkTheme = true) {
        SelectionItem(isSelected = true, description = "Text")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionItemPreviewWithoutIconAndNotSelected() {
    CineVerseTheme(isDarkTheme = true) {
        SelectionItem(type = SelectionType.CHIP, isSelected = false, description = "Text")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionItemPreviewWithoutIcon() {
    CineVerseTheme(isDarkTheme = true) {
        SelectionItem(type = SelectionType.CHIP, isSelected = true, description = "Text")
    }
}