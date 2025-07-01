package com.giraffe.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        modifier = modifier
            .background(Theme.color.background.card),
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary,
        )
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(Theme.radius.lg))
                .background(Theme.color.shade.quaternary)
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
                    onButtonClick = {},
                    isDanger = false,
                    hasBottomDivider = true,
                )
                MenuItem(
                    icon = Theme.icons.dueTone.language,
                    title = "Language",
                    hasSwitch = false,
                    onSwitchChange = {},
                    hasButton = true,
                    onButtonClick = {},
                    isDanger = false,
                    hasBottomDivider = true,
                )
                MenuItem(
                    icon = Theme.icons.dueTone.colorSwitch,
                    title = "Preferences",
                    hasSwitch = false,
                    onSwitchChange = {},
                    hasButton = true,
                    onButtonClick = {},
                    isDanger = false,
                    hasBottomDivider = true,
                )
                MenuItem(
                    icon = Theme.icons.dueTone.logout,
                    title = "Logout",
                    hasSwitch = false,
                    onSwitchChange = {},
                    hasButton = true,
                    onButtonClick = {},
                    isDanger = true,
                    hasBottomDivider = false,
                )
            }
        )
    }
}