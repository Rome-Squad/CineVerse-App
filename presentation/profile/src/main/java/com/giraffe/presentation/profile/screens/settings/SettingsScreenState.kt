package com.giraffe.presentation.profile.screens.settings

import com.giraffe.presentation.profile.model.UserUi
import com.giraffe.presentation.profile.utils.Language
import com.giraffe.user.entity.ContentPreference


data class SettingsScreenState(
    val isLoading: Boolean = true,
    val isNoInternet: Boolean = false,
    val isLoggedIn: Boolean = false,
    val user: UserUi? = null,
    val isDarkMode: Boolean = false,
    val currentLanguage: Language = Language.ENGLISH,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
    val showEditProfileSheet: Boolean = false,
    val showChangeLanguageSheet: Boolean = false,
    val appVersion: String = "",
    val showLogoutSheet: Boolean = false,
    val showContentPreferencesSheet: Boolean = false
)