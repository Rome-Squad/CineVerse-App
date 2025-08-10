package com.giraffe.presentation.profile.screens.settings

import androidx.lifecycle.viewModelScope
import com.giraffe.media.exception.NoInternetException
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.utils.Language
import com.giraffe.presentation.profile.utils.LanguageHelper
import com.giraffe.presentation.profile.utils.toUi
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
) : BaseViewModel<SettingsScreenState, SettingsEffect>(SettingsScreenState()),
    SettingsInteractionListener {

    init {
        checkLoginStatus()
        observeTheme()
        observeLanguage()
        observeContentPreference()
    }

    private fun checkLoginStatus() {
        safeExecute(
            onSuccess = ::handleLoginStatusSuccess,
            onError = ::onFailure,
            block = isLoggedInUseCase::invoke
        )
    }

    private fun handleLoginStatusSuccess(isLoggedIn: Boolean) {
        updateState { it.copy(isLoading = false, isNoInternet = false, isLoggedIn = isLoggedIn) }
        if (isLoggedIn) getUserProfile()
    }

    private fun observeTheme() {
        getDarkModeUseCase().onEach { isDark ->
            updateState { it.copy(isDarkMode = isDark) }
        }.launchIn(viewModelScope)
    }

    private fun observeLanguage() {
        getLanguageUseCase().onEach { langCode ->
            val language = Language.entries.firstOrNull { it.code == langCode } ?: Language.ARABIC
            updateState { it.copy(currentLanguage = language) }
        }.launchIn(viewModelScope)
    }

    private fun observeContentPreference() {
        getContentPreferenceUseCase().onEach { preference ->
            updateState { it.copy(contentPreference = preference) }
        }.launchIn(viewModelScope)
    }

    private fun getUserProfile() {
        safeExecute(
            onSuccess = ::handleGetUserProfileSuccess,
            onError = ::onFailure,
            block = getUserUseCase::invoke
        )
    }

    private fun handleGetUserProfileSuccess(user: User) {
        updateState { it.copy(isLoading = false, isNoInternet = false, user = user.toUi()) }
    }

    private fun onFailure(error: Throwable) {
        updateState { it.copy(isLoading = false, isNoInternet = error is NoInternetException) }
        sendEffect(SettingsEffect.ShowError(error))
    }

    override fun onLoginClick() {
        sendEffect(SettingsEffect.NavigateToLogin)
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
        safeExecute(
            onSuccess = { onDismissSheet() },
            onError = ::onFailure,
            block = { setContentPreferenceUseCase(preference) }
        )
    }

    override fun onNavigateToMyCollections() {
        sendEffect(SettingsEffect.NavigateToMyCollections)
    }

    override fun onNavigateToHistory() {
        sendEffect(SettingsEffect.NavigateToHistory)
    }

    override fun onNavigateToRatings() {
        sendEffect(SettingsEffect.NavigateToRatings)
    }

    override fun onLanguageChange(languageCode: String) {
        safeExecute(
            onSuccess = {
                LanguageHelper.updateAppLocale(languageCode)
                onDismissSheet()
            },
            onError = ::onFailure,
            block = { setLanguageUseCase(languageCode) }
        )
    }

    override fun onToggleDarkMode(isDark: Boolean) {
        safeExecute(
            onError = ::onFailure,
            block = { setDarkModeUseCase(isDark) }
        )
    }

    override fun onConfirmLogout() {
        onDismissSheet()
        safeExecute(
            onSuccess = {
                sendEffect(SettingsEffect.NavigateToLogin)
            },
            onError = ::onFailure,
            block = logoutUseCase::invoke
        )
    }

    override fun onGoToWebsiteClick() {
        onDismissSheet()
        sendEffect(SettingsEffect.NavigateToEditProfileWebView)
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
}