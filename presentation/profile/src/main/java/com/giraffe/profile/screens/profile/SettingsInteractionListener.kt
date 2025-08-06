package com.giraffe.profile.screens.profile

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
}