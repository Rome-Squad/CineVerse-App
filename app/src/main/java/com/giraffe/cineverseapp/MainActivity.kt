package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.giraffe.designsystem.theme.CineVerseTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import com.giraffe.explore.ExploreApi
import com.giraffe.home.HomeApi
import org.koin.android.ext.android.inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authApi: AuthenticationApi

    val homeApi: HomeApi by inject()
    val exploreApi: ExploreApi by inject()
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
                MainScreen(exploreApi, homeApi)
            }
        }
    }
}