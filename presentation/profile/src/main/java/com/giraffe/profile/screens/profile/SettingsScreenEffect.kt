package com.giraffe.profile.screens.profile

sealed interface SettingsScreenEffect {
    data object NavigateToLogin : SettingsScreenEffect
    data object NavigateToEditProfileWebsite : SettingsScreenEffect
    data object NavigateToSplash : SettingsScreenEffect
    data class ShowError(val message: String) : SettingsScreenEffect
    data object NavigateToEditProfileWebView : SettingsScreenEffect
}