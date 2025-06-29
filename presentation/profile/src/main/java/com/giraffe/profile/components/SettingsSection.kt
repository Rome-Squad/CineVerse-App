package com.giraffe.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.presentation.designsystem.theme.Theme

@Composable
fun SettingsSection(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
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
                .background(Theme.color.shade.quaternary)
        ) {
            content()
        }
    }

}


@Preview
@Composable
fun SettingsSectionPreview() {
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
                hasBottomDivider = true,
            )
        }
    )
}