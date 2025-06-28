package com.giraffe.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.designsystem.icon.CineVerseIcons
import com.giraffe.presentation.designsystem.theme.Theme

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    hasSwitch: Boolean,
    onSwitchChange: (Boolean) -> Unit,
    hasButton: Boolean,
    onButtonClick: () -> Unit,
    isDanger: Boolean = false,
) {
    Row(
        modifier = modifier
            .size(48.dp)
            .background(Theme.color.shade.quaternary!!),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = if (isDanger) Theme.color.additional.primary.red else Theme.color.shade.primary,
                modifier = Modifier
                    .size(24.dp)
            )
            Text(
                text = title,
                modifier = Modifier
                    .weight(1f),
                color = if (isDanger) Theme.color.shade.quaternary!! else Theme.color.shade.primary,
                fontStyle = Theme.textStyle.body.medium.medium.fontStyle,
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            if (hasSwitch) {
                // switch from design system
            }
            if (hasButton) {
                Icon(
                    painter = painterResource(id = CineVerseIcons().outline.altArrowRight),
                    contentDescription = "Navigate to $title",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onButtonClick() }
                )
            }
        }
    }
}


@Preview
@Composable
fun MenuItemPreview() {
    val cineVerseIcons = CineVerseIcons()
    MenuItem(
        icon = cineVerseIcons.dueTone.moon,
        title = "Profile",
        hasSwitch = true,
        onSwitchChange = {},
        hasButton = true,
        onButtonClick = {}
    )
}

