package com.giraffe.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.designsystem.icon.CineVerseIcons
import com.giraffe.presentation.designsystem.theme.CineVerseTheme
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
    hasBottomDivider: Boolean = false
) {
    Column(
        modifier = modifier
            .background(Theme.color.shade.quaternary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.Start
                ),
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
                    modifier = Modifier,
                    color = if (isDanger) Theme.color.additional.primary.red else Theme.color.shade.primary,
                    style = Theme.textStyle.body.md.medium,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.End
                ),
                modifier = Modifier
            ) {
                if (hasSwitch) {
                    // switch from design system
                    Switch(
                        checked = false, // This should be a state variable
                        onCheckedChange = onSwitchChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = if (isDanger) Theme.color.additional.primary.red else Theme.color.shade.primary,
                            uncheckedThumbColor = Theme.color.shade.primary
                        ),
                        modifier = Modifier
                    )
                }
                if (hasButton) {
                    Icon(
                        painter = painterResource(id = CineVerseIcons().outline.altArrowRight),
                        contentDescription = "Navigate to $title",
                        tint = if (isDanger) Theme.color.additional.primary.red else Theme.color.shade.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onButtonClick() }
                    )
                }
            }
        }
        if (hasBottomDivider) {
            Divider(
                color = Theme.color.shade.tertiary,
                thickness = 1.dp
            )
        }
    }
}


@Preview
@Composable
fun MenuItemPreview() {
    CineVerseTheme(isDarkTheme = true) {
        MenuItem(
            modifier = Modifier.width(800.dp),
            icon = Theme.icons.dueTone.moon,
            title = "Profile",
            hasSwitch = true,
            onSwitchChange = {},
            hasButton = false,
            onButtonClick = {},
            isDanger = false,
            hasBottomDivider = true
        )
    }
}

