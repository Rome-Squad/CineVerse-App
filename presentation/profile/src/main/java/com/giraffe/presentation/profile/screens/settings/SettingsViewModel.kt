package com.giraffe.presentation.profile.screens.settings

import com.giraffe.media.collections.usecase.ClearCollectionsCacheUseCase
import com.giraffe.media.movie.usecase.ClearMoviesCacheUseCase
import com.giraffe.media.movie.usecase.genre.SyncMoviesGenresUseCase
import com.giraffe.media.movie.usecase.recentlyViewed.SyncRecentlyViewedMoviesUseCase
import com.giraffe.media.series.usecase.ClearSeriesCacheUseCase
import com.giraffe.media.series.usecase.genre.SyncSeriesGenresUseCase
import com.giraffe.media.series.usecase.recentlyViewed.SyncRecentlyViewedSeriesUseCase
import com.giraffe.presentation.profile.base.BaseViewModel
import com.giraffe.presentation.profile.utils.AppVersionProvider
import com.giraffe.presentation.profile.utils.Language
import com.giraffe.presentation.profile.utils.LanguageHelper
import com.giraffe.presentation.profile.utils.toUi
import com.giraffe.user.entity.ContentPreference
import com.giraffe.user.usecase.GetContentPreferenceUseCase
import com.giraffe.user.usecase.GetDarkModeUseCase
import com.giraffe.user.usecase.GetLanguageUseCase
import com.giraffe.user.usecase.GetUserUseCase
import com.giraffe.user.usecase.IsLoggedInByAccountUseCase
import com.giraffe.user.usecase.LogoutUseCase
import com.giraffe.user.usecase.RefreshUserUseCase
import com.giraffe.user.usecase.SetContentPreferenceUseCase
import com.giraffe.user.usecase.SetDarkModeUseCase
import com.giraffe.user.usecase.SetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val isLoggedInByAccountUseCase: IsLoggedInByAccountUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val refreshUserUseCase: RefreshUserUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val appVersionProvider: AppVersionProvider,
    private val logoutUseCase: LogoutUseCase,
    private val clearMoviesCacheUseCase: ClearMoviesCacheUseCase,
    private val clearSeriesCacheUseCase: ClearSeriesCacheUseCase,
    private val syncRecentlyViewedMoviesUseCase: SyncRecentlyViewedMoviesUseCase,
    private val syncRecentlyViewedSeriesUseCase: SyncRecentlyViewedSeriesUseCase,
    private val syncMoviesGenresUseCase: SyncMoviesGenresUseCase,
    private val syncSeriesGenresUseCase: SyncSeriesGenresUseCase,
    private val getContentPreferenceUseCase: GetContentPreferenceUseCase,
    private val setContentPreferenceUseCase: SetContentPreferenceUseCase,
    private val clearCollectionsCacheUseCase: ClearCollectionsCacheUseCase
) : BaseViewModel<SettingsScreenState, SettingsEffect>(SettingsScreenState()),
    SettingsInteractionListener {

    init {
        observeTheme()
        observeUserProfile()
        observeLanguage()
        observeContentPreference()
        loadAppVersion()
    }

    private fun loadAppVersion() {
        val versionName = appVersionProvider.getVersionName()
        updateState { it.copy(appVersion = versionName) }
    }

    private fun observeUserProfile() {
        safeExecute(
            onError = ::onFailure,
            onSuccess = ::onObserveUserProfileSuccess,
            block = isLoggedInByAccountUseCase::invoke
        )
    }

    private fun onObserveUserProfileSuccess(isLoggedIn: Boolean) {
        if (isLoggedIn) safeCollect(
            onEmitNewValue = { user ->
                updateState {
                    it.copy(
                        isLoggedIn = user != null,
                        user = user?.toUi()
                    )
                }
            },
            onError = ::onFailure,
            block = { getUserUseCase() }
        )
    }

    override fun refreshUserProfile() {
        safeExecute(
            onError = ::onFailure,
            block = refreshUserUseCase::invoke
        )
    }

    private fun observeTheme() {
        safeCollect(
            onError = ::onFailure,
            onEmitNewValue = ::onThemeChangedSuccess,
            block = getDarkModeUseCase::invoke
        )
    }

    fun onThemeChangedSuccess(isDark: Boolean) {
        updateState { it.copy(isDarkMode = isDark) }
    }

    private fun observeLanguage() {
        safeCollect(
            onError = ::onFailure,
            onEmitNewValue = ::onLanguageChangedSuccess,
            block = getLanguageUseCase::invoke
        )
    }

    fun onLanguageChangedSuccess(langCode: String) {
        updateState {
            it.copy(
                currentLanguage = Language.entries.firstOrNull { language -> language.code == langCode }
                    ?: Language.ARABIC
            )
        }
    }

    private fun observeContentPreference() {
        safeCollect(
            onError = ::onFailure,
            onEmitNewValue = ::onContentPreferenceChangedSuccess,
            block = getContentPreferenceUseCase::invoke
        )
    }

    fun onContentPreferenceChangedSuccess(preference: ContentPreference) {
        updateState { it.copy(contentPreference = preference) }
    }

    private fun onFailure(error: Throwable, isNoInternet: Boolean) {
        updateState {
            it.copy(
                isLoading = false,
                isLoggingOut = false,
                isNoInternet = isNoInternet
            )
        }
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
        if (languageCode == state.value.currentLanguage.code) {
            onDismissSheet()
            return
        }
        safeExecute(
            dispatcher = Dispatchers.Main,
            onSuccess = { onLanguageChangeSuccess(languageCode) },
            onError = ::onFailure,
            block = { setLanguageUseCase(languageCode) }
        )
    }

    private fun onLanguageChangeSuccess(languageCode: String) {
        LanguageHelper.updateAppLocale(languageCode)
        safeExecute {
            clearMoviesCacheUseCase.clearExceptRecentlyViewed()
            syncRecentlyViewedMoviesUseCase.invoke()
            syncMoviesGenresUseCase.invoke()

            clearSeriesCacheUseCase.clearExceptRecentlyViewed()
            syncRecentlyViewedSeriesUseCase.invoke()
            syncSeriesGenresUseCase.invoke()
        }
        onDismissSheet()
    }

    override fun onToggleDarkMode(isDark: Boolean) {
        safeExecute(
            onError = ::onFailure,
            block = { setDarkModeUseCase(isDark) }
        )
    }

    override fun onConfirmLogout() {
        onDismissSheet()
        updateState { it.copy(isLoggingOut = true) }
        safeExecute(
            onSuccess = onConfirmLogoutSuccess(),
            onError = ::onFailure
        ) {
            logoutUseCase()

        }
    }

    private fun onConfirmLogoutSuccess(): (Unit) -> Unit = {
        safeExecute(
            onError = ::onFailure,
        ) {
            clearMoviesCacheUseCase.clearAll()
            clearSeriesCacheUseCase.clearAll()
            clearCollectionsCacheUseCase()
        }
        sendEffect(SettingsEffect.NavigateToLogin)
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