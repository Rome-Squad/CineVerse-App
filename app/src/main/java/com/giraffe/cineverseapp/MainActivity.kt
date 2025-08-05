package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.lifecycleScope
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.profile.screens.profile.SettingsScreen
import com.giraffe.profile.utils.LanguageHelper
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authenticationApi: AuthenticationApi

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.language.collect { langCode ->
                LanguageHelper.updateAppLocale(langCode)
            }
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )
        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState(initial = false)
            CineVerseTheme(isDarkTheme = isDarkMode) {
                SettingsScreen(
                    onNavigateToEditProfileWebView = {},
                    onNavigateToLogin = {}
                )
                //authenticationApi.LoginContainer {  }
            }
        }
    }
}