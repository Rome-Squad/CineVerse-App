package com.giraffe.cineverseapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.designsystem.theme.CineVerseTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authenticationApi: AuthenticationApi

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            CineVerseTheme {
                val state by mainViewModel.state.collectAsState()

                authenticationApi.LoginContainer(
                    onBack = {},
                    isOnboardingFirstTime = state.isOnBoardingFirstTime == true,
                    isLoggedIn = state.isLoggedIn == true
                )
            }
        }
    }
}