package com.giraffe.presentation.profile.screens.settings

sealed interface SettingsEffect {
    data object NavigateToEditProfileWebView : SettingsEffect
    data object NavigateToLogin : SettingsEffect
    data object NavigateToMyCollections : SettingsEffect
    data object NavigateToHistory : SettingsEffect
    data object NavigateToRatings : SettingsEffect
    data class ShowError(val error: Throwable) : SettingsEffect
}