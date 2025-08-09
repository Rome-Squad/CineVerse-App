package com.giraffe.presentation.profile.screens.settings

sealed interface SettingsEffect {
    data object NavigateToLogin : SettingsEffect
    data class ShowError(val error: Throwable) : SettingsEffect
    data object NavigateToEditProfileWebsite : SettingsEffect
}