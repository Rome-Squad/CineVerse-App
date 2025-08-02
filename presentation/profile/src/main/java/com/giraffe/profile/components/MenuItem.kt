package com.giraffe.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.Switch
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.icon.CineVerseIcons
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    hasSwitch: Boolean,
    onSwitchChange: (Boolean) -> Unit,
    isSwitchOn: Boolean = true,
    hasButton: Boolean,
    onButtonClick: () -> Unit,
    isDanger: Boolean = false,
    hasBottomDivider: Boolean = false
) {
    Column(
        modifier = modifier
            .background(Theme.color.background.card)
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
                    Switch(
                        isOn = isSwitchOn,
                        onCheckedChange = onSwitchChange
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
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(1.dp)
                    .background(Theme.color.shade.quaternary)
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

