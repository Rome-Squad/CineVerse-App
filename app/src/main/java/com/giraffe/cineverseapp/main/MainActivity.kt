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
import com.giraffe.profile.screens.ratings.RatingScreen
import com.giraffe.profile.utils.LanguageHelper
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authenticationApi: AuthenticationApi

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.state.collect { state ->
                    splashScreen.setKeepOnScreenCondition {
                        state.isLoggedIn == null || state.isOnBoardingFirstTime == null
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

            CineVerseTheme {

                RatingScreen(
                )
            }
        }
    }
}