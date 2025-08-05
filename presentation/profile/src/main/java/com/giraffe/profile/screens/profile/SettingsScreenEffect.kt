package com.giraffe.profile.screens.profile

sealed interface SettingsScreenEffect {
    data object NavigateToLogin : SettingsScreenEffect
    data class ShowError(val message: String) : SettingsScreenEffect
    data object NavigateToEditProfileWebsite : SettingsScreenEffect
}