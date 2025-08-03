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
import com.giraffe.onboarding.screen.OnBoardingContent
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
                val images: List<Int> = listOf(
                    R.drawable.onboard1,
                    R.drawable.onboard2,
                    R.drawable.onboard3
                )
                OnBoardingContent(
                    modifier = Modifier,
                    onBoardingImageList = images
                )
                //  onBoardingApi.OnBoardingContainer()
                // authenticationApi.LoginContainer { }
            }
        }
    }
}