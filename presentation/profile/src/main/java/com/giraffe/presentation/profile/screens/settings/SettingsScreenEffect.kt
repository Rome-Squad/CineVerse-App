package com.giraffe.presentation.profile.screens.settings

import androidx.annotation.StringRes

sealed interface SettingsScreenEffect {
    data object NavigateToLogin : SettingsScreenEffect
    data class ShowError(@param:StringRes val messageResId: Int) : SettingsScreenEffect
    data object NavigateToEditProfileWebsite : SettingsScreenEffect
}