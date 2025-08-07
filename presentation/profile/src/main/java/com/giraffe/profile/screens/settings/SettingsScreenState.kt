package com.giraffe.profile.screens.settings

import com.giraffe.profile.screens.settings.model.UserUiModel
import com.giraffe.profile.utils.Language
import com.giraffe.user.entity.ContentPreference


data class SettingsScreenState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val user: UserUiModel = UserUiModel(),
    val isDarkMode: Boolean = false,
    val currentLanguage: Language = Language.ENGLISH,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,

    val showEditProfileSheet: Boolean = false,
    val showChangeLanguageSheet: Boolean = false,
    val showLogoutSheet: Boolean = false,
    val showContentPreferencesSheet: Boolean = false
)