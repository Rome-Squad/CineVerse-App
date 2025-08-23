package com.giraffe.match.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.match.model.SelectionType

@Composable
fun SelectionItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    type: SelectionType = SelectionType.CARD,
    icon: Painter? = painterResource(Theme.icons.dueTone.headphone),
    description: String,
    secondDescription: String? = null,
    isSecondaryCardType: Boolean = false,
    horizontalPadding: Dp? = null,
    verticalPadding: Dp? = null,
    onClick: () -> Unit = {}
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.tertiary else Theme.color.background.card,
        label = "BackgroundColor"
    )
    val iconBackgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.secondary else Theme.color.brand.tertiary,
        label = "IconBackgroundColor"
    )
    val firstTextColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.primary else Theme.color.shade.primary,
        label = "FirstTextColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.secondary else Color.Transparent,
        label = "BorderColor"
    )

    val horizontalPadding = horizontalPadding ?: if (type == SelectionType.CHIP) 20.dp else 12.dp
    val verticalPadding = verticalPadding ?: if (type == SelectionType.CHIP) 12.5.dp else 12.dp

    Row(
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
            .clip(RoundedCornerShape(Theme.radius.lg))
            .clickable(onClick = onClick)
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (type == SelectionType.CHIP) Arrangement.Center else Arrangement.spacedBy(
            12.dp
        )
    ) {
        if (type == SelectionType.CARD) {
            icon?.let {
                Icon(
                    modifier = Modifier
                        .background(
                            color = iconBackgroundColor,
                            shape = RoundedCornerShape(Theme.radius.md)
                        )
                        .padding(8.dp),
                    painter = it,
                    tint = Theme.color.brand.primary,
                    contentDescription = null
                )
            }
        }
        if (isSecondaryCardType && secondDescription != null) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = firstTextColor,
                    text = description,
                    style = Theme.textStyle.body.md.medium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    color = Theme.color.shade.secondary,
                    text = secondDescription,
                    style = Theme.textStyle.body.md.regular,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        } else {
            Text(
                color = firstTextColor,
                text = description,
                style = Theme.textStyle.body.md.medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        SelectionItem(
            modifier = Modifier
                .size(
                    width = 328.dp,
                    height = 56.dp
                ),
            description = "Text",
            horizontalPadding = 12.dp,
            verticalPadding = 12.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionItemPreviewIsSelected() {
    CineVerseTheme(isDarkTheme = true) {
        SelectionItem(
            modifier = Modifier
                .size(
                    width = 328.dp,
                    height = 56.dp
                ),
            isSelected = true,
            description = "Text"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionItemPreviewWithoutIconAndNotSelected() {
    CineVerseTheme(isDarkTheme = true) {
        SelectionItem(
            modifier = Modifier
                .size(
                    width = 69.dp,
                    height = 44.dp
                ),
            type = SelectionType.CHIP,
            isSelected = false,
            description = "Text"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionItemPreviewWithoutIcon() {
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