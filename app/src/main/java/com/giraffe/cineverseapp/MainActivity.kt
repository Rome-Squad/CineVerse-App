package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.onboarding.OnBoardingApi
import com.giraffe.onboarding.R
import com.giraffe.onboarding.screen.OnBoardingContent
import com.giraffe.onboarding.screen.OnBoardingPage
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authenticationApi: AuthenticationApi

    @Inject
    lateinit var onBoardingApi: OnBoardingApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            CineVerseTheme {
                val pages = listOf(
                    OnBoardingPage(
                        imageRes = R.drawable.onboard1,
                        title = "Welcome to Your Movie Universe",
                        subtitle = "Discover, track, and rate your favorite movies & series."
                    ),
                    OnBoardingPage(
                        imageRes = R.drawable.onboard2,
                        title = "Track Everything",
                        subtitle = "Your Watchlist. Your Ratings. All in One Place."
                    ),
                    OnBoardingPage(
                        imageRes = R.drawable.onboard3,
                        title = "Personalized Recommendations",
                        subtitle = "Answer fun questions to get handpicked recommendations."
                    )
                )
                OnBoardingContent(
                    modifier = Modifier,
                    pages = pages
                )
                //  onBoardingApi.OnBoardingContainer()
                // authenticationApi.LoginContainer { }
            }
        }
    }
}