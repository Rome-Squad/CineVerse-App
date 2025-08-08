package com.giraffe.profile.screens.settings

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.giraffe.profile.base.BaseViewModel
import com.giraffe.profile.screens.settings.model.toUserUiModel
import com.giraffe.profile.utils.Language
import com.giraffe.profile.utils.LanguageHelper
import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.entity.User
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import com.giraffe.user.usecase.GetDarkModeUseCase
import com.giraffe.user.usecase.GetLanguageUseCase
import com.giraffe.user.usecase.GetUserUseCase
import com.giraffe.user.usecase.IsLoggedInUseCase
import com.giraffe.user.usecase.LogoutUseCase
import com.giraffe.user.usecase.SetContentPreferenceUseCase
import com.giraffe.user.usecase.SetDarkModeUseCase
import com.giraffe.user.usecase.SetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase,
    private val setContentPreferenceUseCase: SetContentPreferenceUseCase,
) : BaseViewModel<SettingsScreenState, SettingsScreenEffect>(SettingsScreenState()),
    SettingsInteractionListener {

    init {
        checkLoginStatus()
        observeSettings()
        observeContentPreference()
    }

    private fun checkLoginStatus() {
        safeExecute(
            onSuccess = ::handleLoginStatusSuccess,
            onError = { updateState { it.copy(isLoading = false) } }
        ) {
            isLoggedInUseCase()
        }
    }

    private fun handleLoginStatusSuccess(isLoggedIn: Boolean) {
        updateState { it.copy(isLoggedIn = isLoggedIn) }
        if (isLoggedIn) {
            getUserProfile()
        } else {
            updateState { it.copy(isLoading = false) }
        }
    }

    private fun observeSettings() {
        getDarkModeUseCase().onEach { isDark ->
            updateState { it.copy(isDarkMode = isDark) }
        }.launchIn(viewModelScope)

        getLanguageUseCase().onEach { langCode ->
            val language = Language.entries.firstOrNull { it.code == langCode } ?: Language.ARABIC
            updateState { it.copy(currentLanguage = language) }
        }.launchIn(viewModelScope)
    }

    private fun observeContentPreference() {
        getContentPreferenceUseCase().onEach { preference ->
            updateState { it.copy(contentPreference = preference) }
            Log.d("PreferenceUseCase", "Current preference is: $preference")
        }.launchIn(viewModelScope)
    }

    private fun getUserProfile() {
        safeExecute(
            onSuccess = ::handleGetUserProfileSuccess,
            onError = ::handleGetUserProfileError
        ) {
            getUserUseCase()
        }
    }

    private fun handleGetUserProfileSuccess(user: User) {
        updateState { it.copy(isLoading = false, user = user.toUserUiModel()) }
    }

    private fun handleGetUserProfileError(error: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEffect(SettingsScreenEffect.ShowError(mapErrorToResource(error)))
    }

    override fun onLoginClick() {
        sendEffect(SettingsScreenEffect.NavigateToLogin)
    }

    override fun onEditProfileClick() {
        if (state.value.isLoggedIn) {
            updateState { it.copy(showEditProfileSheet = true) }
        } else {
            onLoginClick()
        }
    }

    override fun onLanguageClick() {
        updateState { it.copy(showChangeLanguageSheet = true) }
    }

    override fun onLogoutClick() {
        updateState { it.copy(showLogoutSheet = true) }
    }

    override fun onContentPreferencesClick() {
        updateState { it.copy(showContentPreferencesSheet = true) }
    }

    override fun onContentPreferenceChange(preference: ContentPreference) {
        viewModelScope.launch {
            setContentPreferenceUseCase(preference)
            onDismissSheet()
        }
    }

    override fun onLanguageChange(languageCode: String) {
        viewModelScope.launch {
            setLanguageUseCase(languageCode)
        }
        LanguageHelper.updateAppLocale(languageCode)
        onDismissSheet()
    }

    override fun onToggleDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            setDarkModeUseCase(isDark)
        }
    }

    override fun onConfirmLogout() {
        onDismissSheet()
        safeExecute(
            onSuccess = {
                sendEffect(SettingsScreenEffect.NavigateToLogin)
            },
            onError = ::onLogoutFailure
        ) {
            logoutUseCase()
        }
    }

    private fun onLogoutFailure(error: Throwable) {
        sendEffect(SettingsScreenEffect.ShowError(mapErrorToResource(error)))
    }

    override fun onGoToWebsiteClick() {
        sendEffect(SettingsScreenEffect.NavigateToEditProfileWebsite)
        onDismissSheet()
    }

    override fun onDismissSheet() {
        updateState {
            it.copy(
                showEditProfileSheet = false,
                showChangeLanguageSheet = false,
                showLogoutSheet = false,
                showContentPreferencesSheet = false
            )
        }
    }

    override fun onCancelClick() {
        onDismissSheet()
    }
}