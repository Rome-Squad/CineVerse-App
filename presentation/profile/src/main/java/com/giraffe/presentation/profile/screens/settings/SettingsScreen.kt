package com.giraffe.presentation.profile.screens.settings

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.giraffe.designsystem.composable.AppBar
import com.giraffe.designsystem.composable.BaseBottomSheet
import com.giraffe.designsystem.composable.MessageInfoBox
import com.giraffe.designsystem.composable.Switch
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.icon.CineVerseIcons
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.profile.R
import com.giraffe.presentation.profile.model.ProfileRowItem
import com.giraffe.presentation.profile.utils.EffectListener
import com.giraffe.presentation.profile.utils.Language
import com.giraffe.presentation.profile.utils.toStringResource
import com.giraffe.user.entity.ContentPreference

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
                is SettingsEffect.NavigateToLogin -> onNavigateToLogin()
                is SettingsEffect.NavigateToEditProfileWebsite -> onNavigateToEditProfileWebView()
                is SettingsEffect.ShowError -> Toast.makeText(
                    context,
                    context.getString(effect.error.toStringResource()),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    SettingsContent(
        state = state,
        interaction = viewModel,
        modifier = Modifier,
        onNavigateToHistory = onNavigateToHistory,
        onNavigateToRatings = onNavigateToRatings,
        onNavigateToMyCollections = onNavigateToMyCollections
    )
}

@Composable
private fun SettingsContent(
    state: SettingsScreenState,
    interaction: SettingsInteractionListener,
    modifier: Modifier,
    onNavigateToMyCollections: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToRatings: () -> Unit
) {
    Column(
        modifier = modifier
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
                .padding(top = 16.dp),
            onNavigateToHistory = onNavigateToHistory,
            onNavigateToRatings = onNavigateToRatings,
            onNavigateToMyCollections = onNavigateToMyCollections
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
            ContentPreferencesContent(
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

@Composable
private fun ContentPreferencesContent(
    modifier: Modifier = Modifier,
    currentPreference: ContentPreference,
    onPreferenceSelected: (ContentPreference) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = stringResource(id = R.string.content_preferences_subtitle),
            style = Theme.textStyle.body.sm.regular,
            color = Theme.color.shade.secondary
        )

        ContentPreferenceItem(
            isSelected = currentPreference == ContentPreference.HIDE_EXPLICIT,
            icon = painterResource(id = Theme.icons.dueTone.eyeSlash),
            title = stringResource(R.string.hide_explicit_content),
            caption = stringResource(R.string.hides_revealing_or_inappropriate_posters),
            onClick = {
                onPreferenceSelected(ContentPreference.HIDE_EXPLICIT)
            }
        )

        ContentPreferenceItem(
            isSelected = currentPreference == ContentPreference.STRICT,
            icon = painterResource(id = Theme.icons.dueTone.slash),
            title = stringResource(R.string.strict_filtering),
            caption = stringResource(R.string.hides_all_content_that_includes_immodest_clothing),
            onClick = {
                onPreferenceSelected(ContentPreference.STRICT)
            }
        )

        ContentPreferenceItem(
            isSelected = currentPreference == ContentPreference.SHOW_ALL,
            icon = painterResource(id = Theme.icons.dueTone.eye),
            title = stringResource(R.string.show_all_content),
            caption = stringResource(R.string.no_filtering_all_images_will_be_displayed),
            onClick = {
                onPreferenceSelected(ContentPreference.SHOW_ALL)
            }
        )
    }
}

@Composable
private fun ContentPreferenceItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    icon: Painter,
    title: String,
    caption: String,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.tertiary else Theme.color.background.bottomSheetCard,
        label = "BackgroundColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.brand.primary else Color.Transparent,
        label = "BorderColor"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.lg))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Theme.radius.lg)
            )
            .background(color = backgroundColor)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(Theme.radius.md),
                    color = if (isSelected) Theme.color.brand.secondary else Theme.color.shade.quaternary
                )
                .padding(8.dp),
            painter = icon,
            contentDescription = title,
            tint = if (isSelected) Theme.color.brand.primary else Theme.color.shade.primary
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.body.md.medium,
                color = if (isSelected) Theme.color.brand.primary else Theme.color.shade.primary
            )
            Text(
                text = caption,
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.shade.secondary
            )
        }
    }
}

@Composable
private fun LanguageSelector(
    modifier: Modifier = Modifier,
    currentLanguage: Language,
    onLanguageChange: (Language) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LanguageSelectorItem(
            modifier = Modifier
                .size(
                    width = 146.dp,
                    height = 87.dp
                )
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .clickable {
                    onLanguageChange(Language.ENGLISH)
                },
            language = Language.ENGLISH,
            isSelected = currentLanguage == Language.ENGLISH,
        )

        LanguageSelectorItem(
            modifier = Modifier
                .size(
                    width = 146.dp,
                    height = 87.dp
                )
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .clickable {
                    onLanguageChange(Language.ARABIC)
                },
            language = Language.ARABIC,
            isSelected = currentLanguage == Language.ARABIC
        )
    }
}

@Composable
private fun LanguageSelectorItem(
    modifier: Modifier = Modifier,
    language: Language,
    isSelected: Boolean,
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected)
            Theme.color.brand.primary
        else
            Theme.color.shade.primary
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected)
            Theme.color.brand.secondary
        else
            Theme.color.shade.quaternary
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected)
            Theme.color.brand.tertiary
        else
            Theme.color.shade.quaternary
    )

    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(
                    size = Theme.radius.lg
                )
            )
            .clip(
                shape = RoundedCornerShape(
                    size = Theme.radius.lg
                )
            )
            .background(
                color = backgroundColor
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        language.flag()

        Text(
            text = stringResource(
                id = language.label
            ),
            style = Theme.textStyle.body.md.medium,
            color = textColor
        )
    }

}

@Composable
private fun MenuItem(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    hasSwitch: Boolean,
    onSwitchChange: (Boolean) -> Unit,
    isSwitchOn: Boolean = true,
    hasButton: Boolean,
    onRowItemClick: () -> Unit,
    isDanger: Boolean = false,
    hasBottomDivider: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (hasButton) {
                    Modifier.noHoverClickable { onRowItemClick() }
                } else {
                    Modifier
                }
            )
            .background(Theme.color.background.card)
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
                tint = if (isDanger) Theme.color.additional.primary.red else Theme.color.brand.primary,
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

@Composable
private fun ProfileLazyRow(
    modifier: Modifier = Modifier,
    onNavigateToMyCollections: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToRatings: () -> Unit
) {

    val items = listOf(
        ProfileRowItem(
            textResId = com.giraffe.designsystem.R.string.history,
            iconResId = com.giraffe.designsystem.R.drawable.outline_history,
            onClick = onNavigateToHistory
        ),
        ProfileRowItem(
            textResId = com.giraffe.designsystem.R.string.my_collections,
            iconResId = com.giraffe.designsystem.R.drawable.due_tone_video_library,
            onClick = onNavigateToMyCollections
        ),
        ProfileRowItem(
            textResId = com.giraffe.designsystem.R.string.my_ratings,
            iconResId = com.giraffe.designsystem.R.drawable.due_tone_star,
            onClick = onNavigateToRatings
        )
    )

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(items) { item ->
            IconTextBox(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = Theme.radius.full))
                    .clickable(
                        onClick = { item.onClick() }
                    )
                    .background(color = Theme.color.background.card)
                    .padding(vertical = 8.dp)
                    .padding(start = 10.dp, end = 12.dp),
                text = stringResource(id = item.textResId)
            ) {
                Icon(
                    painter = painterResource(id = item.iconResId),
                    contentDescription = stringResource(id = item.textResId),
                    tint = Theme.color.brand.primary
                )
            }
        }
    }
}

@Composable
private fun IconTextBox(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = Theme.textStyle.label.md.medium,
    textColor: Color = Theme.color.shade.primary,
    icon: @Composable () -> Unit,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon()

        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }

}

@Composable
private fun SettingsSection(
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

@Composable
private fun UserProfileSection(
    modifier: Modifier = Modifier,
    userProfileImage: String,
    userDisplayName: String,
    username: String,
    onRowClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    size = Theme.radius.lg
                )
            )
            .background(
                color = Theme.color.background.card
            )
            .padding(
                all = 16.dp
            )
            .noHoverClickable(
                onRowClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (userProfileImage.isNotBlank()) {
                AsyncImage(
                    model = userProfileImage,
                    contentDescription = userDisplayName,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            shape = CircleShape,
                            color = Theme.color.shade.quinary
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Theme.icons.dueTone.profile),
                        contentDescription = "profile Image",
                        tint = Theme.color.shade.secondary
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = userDisplayName,
                    style = Theme.textStyle.body.md.medium,
                    color = Theme.color.shade.primary
                )


                Text(
                    text = username,
                    style = Theme.textStyle.body.sm.regular,
                    color = Theme.color.shade.secondary
                )
            }
        }

        Icon(
            painter = painterResource(
                id = com.giraffe.designsystem.R.drawable.outline_alt_arrow_right
            ),
            contentDescription = stringResource(
                id = com.giraffe.designsystem.R.string.right_arrow
            ),
            tint = Theme.color.shade.tertiary,
        )
    }
}