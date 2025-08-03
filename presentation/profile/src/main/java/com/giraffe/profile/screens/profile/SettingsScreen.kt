package com.giraffe.profile.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.profile.R
import com.giraffe.profile.components.MenuItem
import com.giraffe.profile.components.ProfileLazyRow
import com.giraffe.profile.components.SettingsSection
import com.giraffe.profile.components.UserProfileSection

@Composable
fun SettingsScreen() {
    SettingsContent(
        modifier = Modifier
    )
}

@Composable
fun SettingsContent(
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Theme.color.background.screen)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(R.string.My_Profile),
        )
        UserProfileSection(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .background(
                    color = Theme.color.background.screen
                ),
            userProfileImage = "",
            userDisplayName = "hamada rayyan",
            username = "hamada_rayyan"
        ) {

        }

        ProfileLazyRow(
            modifier = Modifier
                .padding(top = 16.dp)
        )
        SettingsSection(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
            title = stringResource(R.string.settings_text)
        ) {
            MenuItem(
                icon = Theme.icons.dueTone.moon,
                title = stringResource(R.string.Dark_Mode),
                hasSwitch = true,
                onSwitchChange = {},
                hasButton = false,
                onButtonClick = {},
                isDanger = false,
                hasBottomDivider = true,
            )
            MenuItem(
                icon = Theme.icons.dueTone.language,
                title = stringResource(R.string.Language),
                hasSwitch = false,
                onSwitchChange = {},
                hasButton = true,
                onButtonClick = {},
                isDanger = false,
                hasBottomDivider = true,
            )
            MenuItem(
                icon = Theme.icons.dueTone.logout,
                title = stringResource(R.string.Logout),
                hasSwitch = false,
                onSwitchChange = {},
                hasButton = true,
                onButtonClick = {},
                isDanger = true,
                hasBottomDivider = false,
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 24.dp),
            text = stringResource(R.string.Version),
            color = Theme.color.shade.tertiary,
            style = Theme.textStyle.body.sm.regular
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CineVerseTheme(isDarkTheme = false) {
        SettingsScreen()
    }
}