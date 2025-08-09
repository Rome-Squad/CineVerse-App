package com.giraffe.presentation.profile.screens.settings

import com.giraffe.user.entity.ContentPreference

interface SettingsInteractionListener {
    fun onLoginClick()
    fun onEditProfileClick()
    fun onLanguageClick()
    fun onLogoutClick()
    fun onToggleDarkMode(isDark: Boolean)
    fun onConfirmLogout()
    fun onGoToWebsiteClick()
    fun onDismissSheet()
    fun onCancelClick()
    fun onContentPreferencesClick()
    fun onLanguageChange(languageCode: String)
    fun onContentPreferenceChange(preference: ContentPreference)
}