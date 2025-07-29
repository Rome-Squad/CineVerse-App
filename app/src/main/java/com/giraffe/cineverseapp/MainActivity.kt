package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.cineverseapp.data.preference.DataStorePreferences
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.home.HomeApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    private val authApi: AuthenticationApi by inject()
    private val homeApi: HomeApi by inject()
    private val dataStorePreferences: DataStorePreferences by inject()


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
                var isLoggedIn by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    CoroutineScope(Dispatchers.IO).launch {
                        isLoggedIn = dataStorePreferences.isLoggedIn()
                    }
                }
                if (isLoggedIn) homeApi.HomeContainer() else authApi.LoginContainer {}
            }
        }
    }
}