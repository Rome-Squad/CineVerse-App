package com.giraffe.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun SettingsSection(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
        )
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(Theme.radius.lg))
                .background(Theme.color.background.card)
        ) {
            content()
        }
    }

}


@Preview
@Composable
fun SettingsSectionPreview() {
    CineVerseTheme(isDarkTheme = true) {
        SettingsSection(
            title = "Settings",
            content = {
                MenuItem(
                    icon = Theme.icons.dueTone.moon,
                    title = "Dark Mode",
                    hasSwitch = true,
                    onSwitchChange = {},
                    hasButton = false,
                    onRowItemClick = {},
                    isDanger = false,
                    hasBottomDivider = true,
                )
                MenuItem(
                    icon = Theme.icons.dueTone.language,
                    title = "Language",
                    hasSwitch = false,
                    onSwitchChange = {},
                    hasButton = true,
                    onRowItemClick = {},
                    isDanger = false,
                    hasBottomDivider = true,
                )
                MenuItem(
                    icon = Theme.icons.dueTone.colorSwitch,
                    title = "Preferences",
                    hasSwitch = false,
                    onSwitchChange = {},
                    hasButton = true,
                    onRowItemClick = {},
                    isDanger = false,
                    hasBottomDivider = true,
                )
                MenuItem(
                    icon = Theme.icons.dueTone.logout,
                    title = "Logout",
                    hasSwitch = false,
                    onSwitchChange = {},
                    hasButton = true,
                    onRowItemClick = {},
                    isDanger = true,
                    hasBottomDivider = false,
                )
            }
        )
    }
}