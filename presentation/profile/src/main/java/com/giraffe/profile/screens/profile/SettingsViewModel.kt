package com.giraffe.profile.screens.profile

import androidx.lifecycle.viewModelScope
import com.giraffe.profile.base.BaseViewModel
import com.giraffe.profile.screens.profile.model.toUserUiModel
import com.giraffe.profile.utils.Language
import com.giraffe.profile.utils.LanguageHelper
import com.giraffe.user.usecase.GetDarkModeUseCase
import com.giraffe.user.usecase.GetLanguageUseCase
import com.giraffe.user.usecase.GetUserUseCase
import com.giraffe.user.usecase.IsLoggedInUseCase
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
) : BaseViewModel<SettingsScreenState, SettingsScreenEffect>(SettingsScreenState()),
    SettingsInteractionListener {

    init {
        checkLoginStatus()
        observeSettings()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val isLoggedIn = isLoggedInUseCase()
            updateState { it.copy(isLoggedIn = isLoggedIn) }
            if (isLoggedIn) {
                getUserProfile()
            } else {
                updateState { it.copy(isLoading = false) }
            }
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

    private fun getUserProfile() {
        safeExecute(
            onSuccess = { user ->
                updateState {
                    it.copy(
                        isLoading = false,
                        user = user.toUserUiModel()
                    )
                }
            },
            onError = { error ->
                updateState { it.copy(isLoading = false) }
                sendEffect(SettingsScreenEffect.ShowError(error.message.toString()))
            }
        ) {
            getUserUseCase()
        }
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