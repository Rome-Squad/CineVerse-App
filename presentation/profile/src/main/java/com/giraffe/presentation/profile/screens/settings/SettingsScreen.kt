package com.giraffe.presentation.profile.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.presentation.profile.components.ScreenStates
import com.giraffe.presentation.profile.screens.settings.components.ContentPreferences
import com.giraffe.presentation.profile.screens.settings.components.LanguageSelector
import com.giraffe.presentation.profile.screens.settings.components.MenuItem
import com.giraffe.presentation.profile.screens.settings.components.ProfileShortcuts
import com.giraffe.presentation.profile.screens.settings.components.SettingsSection
import com.giraffe.presentation.profile.screens.settings.components.UserProfileSection
import com.giraffe.presentation.profile.utils.EffectListener
import com.giraffe.presentation.profile.utils.showToast
import com.giraffe.presentation.profile.utils.toStringResource

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToEditProfileWebView: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToMyCollections: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToRatings: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    EffectListener(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingsEffect.NavigateToEditProfileWebView -> onNavigateToEditProfileWebView()
                SettingsEffect.NavigateToHistory -> onNavigateToHistory()
                SettingsEffect.NavigateToLogin -> onNavigateToLogin()
                SettingsEffect.NavigateToMyCollections -> onNavigateToMyCollections()
                SettingsEffect.NavigateToRatings -> onNavigateToRatings()
                is SettingsEffect.ShowError -> context.showToast(effect.error.toStringResource())
            }
        }
    }
    SettingsContent(
        state = state,
        interaction = viewModel
    )
}

@Composable
private fun SettingsContent(
    state: SettingsScreenState,
    interaction: SettingsInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Theme.color.background.screen)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(R.string.My_Profile),
        )
        ScreenStates(
            isLoading = state.isLoading,
            isNoInternet = state.isNoInternet,
        ) {
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
            ProfileShortcuts(
                modifier = Modifier
                    .padding(top = 16.dp),
                onNavigateToHistory = interaction::onNavigateToHistory,
                onNavigateToRatings = interaction::onNavigateToRatings,
                onNavigateToMyCollections = interaction::onNavigateToMyCollections
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
                    hasBottomDivider = state.isLoggedIn,
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
                text = stringResource(R.string.app_version, state.appVersion),
                color = Theme.color.shade.tertiary,
                style = Theme.textStyle.body.sm.regular
            )
        }
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
                onClickPrimaryButton = interaction::onGoToWebsiteClick,
                onClickSecondaryButton = interaction::onDismissSheet,
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
            ContentPreferences(
                currentPreference = state.contentPreference,
                onPreferenceSelected = interaction::onContentPreferenceChange
            )
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
                onClickSecondaryButton = interaction::onDismissSheet,
                onClickPrimaryButton = interaction::onConfirmLogout
            )
        }
    )
}