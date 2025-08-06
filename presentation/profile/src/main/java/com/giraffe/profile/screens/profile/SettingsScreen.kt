package com.giraffe.profile.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.profile.R
import com.giraffe.profile.components.LanguageSelector
import com.giraffe.profile.components.MenuItem
import com.giraffe.profile.components.ProfileLazyRow
import com.giraffe.profile.components.SettingsSection
import com.giraffe.profile.components.UserProfileSection
import com.giraffe.profile.utils.Language

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToEditProfileWebView: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SettingsScreenEffect.NavigateToLogin -> onNavigateToLogin()
                is SettingsScreenEffect.NavigateToEditProfileWebsite -> onNavigateToEditProfileWebView()
                else -> {}
            }
        }
    }

    SettingsContent(
        state = state,
        interaction = viewModel,
        modifier = Modifier,
    )
}

@Composable
fun SettingsContent(
    state: SettingsScreenState,
    interaction: SettingsInteractionListener,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Theme.color.background.screen)
            .statusBarsPadding(),
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
            userProfileImage = if (state.isLoggedIn) state.user.imageUrl else "",
            userDisplayName = if (state.isLoggedIn) state.user.name else stringResource(R.string.login_or_sign_up),
            username = if (state.isLoggedIn) "@${state.user.username}" else stringResource(R.string.to_personalize_your_experience),
            onRowClick = {
                if (state.isLoggedIn) interaction.onEditProfileClick() else interaction.onLoginClick()
            }
        )

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
                hasButton = false,
                isSwitchOn = state.isDarkMode,
                onSwitchChange = interaction::onToggleDarkMode,
                onRowItemClick = {},
                isDanger = false,
                hasBottomDivider = true,
            )
            MenuItem(
                icon = Theme.icons.dueTone.language,
                title = stringResource(R.string.Language),
                hasSwitch = false,
                onSwitchChange = {},
                hasButton = true,
                onRowItemClick = interaction::onLanguageClick,
                isDanger = false,
                hasBottomDivider = true,
            )
            MenuItem(
                icon = Theme.icons.dueTone.colorSwitch,
                title = stringResource(R.string.content_preferences),
                hasSwitch = false,
                hasButton = true,
                onRowItemClick = interaction::onContentPreferencesClick,
                isDanger = false,
                hasBottomDivider = true,
                onSwitchChange = {},
            )
            if (state.isLoggedIn) {
                MenuItem(
                    icon = Theme.icons.dueTone.logout,
                    title = stringResource(R.string.Logout),
                    hasSwitch = false,
                    onSwitchChange = {},
                    hasButton = true,
                    onRowItemClick = interaction::onLogoutClick,
                    isDanger = true,
                    hasBottomDivider = false,
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(top = 24.dp),
            text = stringResource(R.string.Version),
            color = Theme.color.shade.tertiary,
            style = Theme.textStyle.body.sm.regular
        )
    }
    BaseBottomSheet(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 28.dp),
        isVisible = state.showEditProfileSheet,
        onDismiss = interaction::onDismissSheet,
        content = {
            MessageInfoBox(
                title = stringResource(R.string.edit_profile_title),
                caption = stringResource(R.string.edit_profile_caption),
                icon = painterResource(Theme.icons.dueTone.linkMinimalistic),
                buttonBackgroundColor = Theme.color.brand.primary,
                iconTintColor = Theme.color.brand.primary,
                iconBackgroundColor = Theme.color.brand.tertiary,
                titlePrimaryButton = stringResource(R.string.edit_profile_primary_button),
                titleSecondaryButton = stringResource(R.string.cancel),
                modifier = Modifier.size(304.dp, 214.dp)
            )
        }
    )

    BaseBottomSheet(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 28.dp),
        isVisible = state.showChangeLanguageSheet,
        onDismiss = interaction::onDismissSheet,
        title = stringResource(R.string.change_language_title),
        content = {
            LanguageSelector(
                currentLanguage = state.currentLanguage,
                onLanguageChange = { newLanguage ->
                    interaction.onLanguageChange(newLanguage.code)
                }
            )
        }
    )

    BaseBottomSheet(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 28.dp),
        isVisible = state.showContentPreferencesSheet,
        onDismiss = interaction::onDismissSheet,
        title = stringResource(R.string.content_preferences_title),
        content = {

        }
    )

    BaseBottomSheet(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 28.dp),
        isVisible = state.showLogoutSheet,
        onDismiss = interaction::onDismissSheet,
        content = {
            MessageInfoBox(
                title = stringResource(R.string.logout_dialog_title),
                caption = stringResource(R.string.logout_dialog_caption),
                icon = painterResource(Theme.icons.dueTone.logout),
                buttonBackgroundColor = Theme.color.additional.primary.red,
                iconTintColor = Theme.color.additional.primary.red,
                iconBackgroundColor = Theme.color.additional.secondary.red,
                titlePrimaryButton = stringResource(R.string.logout_dialog_primary_button),
                titleSecondaryButton = stringResource(R.string.cancel),
                modifier = Modifier.size(304.dp, 214.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CineVerseTheme(isDarkTheme = false) {
        SettingsScreen(
            onNavigateToEditProfileWebView = {},
            onNavigateToLogin = {}
        )
    }
}