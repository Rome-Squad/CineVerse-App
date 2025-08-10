package com.giraffe.cineverseapp.main

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.presentation.profile.utils.LanguageHelper

import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authenticationApi: AuthenticationApi

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.splashState.collectLatest { splash ->
                    if (!splash.keepSplashVisible) {
                        splashScreen.setKeepOnScreenCondition { false }
                        return@collectLatest
                    }
                }
            }
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.auto(
                Color.Companion.Transparent.toArgb(),
                Color.Companion.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.Companion.auto(
                Color.Companion.Transparent.toArgb(),
                Color.Companion.Transparent.toArgb()
            )
        )

        setContent {
            val state by mainViewModel.state.collectAsState()

            CineVerseTheme(
                isDarkTheme = state.isDarkMode
            ) {

                LaunchedEffect(state.language) {
                    LanguageHelper.updateAppLocale(state.language)
                }

                authenticationApi.LoginContainer(
                    onBack = {},
                    isOnboardingFirstTime = state.isOnBoardingFirstTime == true,
                    isLoggedIn = state.isLoggedIn == true
                )
            }
        }
    }
}