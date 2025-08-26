package com.giraffe.presentation.details.navigation.main

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
import androidx.navigation.compose.rememberNavController
import com.giraffe.api.authentication.AuthenticationApi
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.presentation.details.navigation.routes.StartDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    @Inject
    lateinit var authApiProvider: AuthenticationApi
    private val detailsViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDestination: StartDestination? = intent.getStringExtra(START_DESTINATION)?.let {
            Json.decodeFromString(it)
        }
        setContent {
            val state by detailsViewModel.state.collectAsState()
            val isDarkTheme = state.isDarkTheme

            val systemBarsColor = if (isDarkTheme)
                SystemBarStyle.dark(Color.Transparent.toArgb())
            else
                SystemBarStyle.light(
                    Color.Transparent.toArgb(),
                    Color.Transparent.toArgb()
                )

            enableEdgeToEdge(systemBarsColor, systemBarsColor)
            CineVerseTheme(
                isDarkTheme = isDarkTheme
            ) {

                val navController = rememberNavController()

                startDestination?.let { startDestination ->
                    DetailsNavGraph(
                        navController = navController,
                        startDestinationRoute = startDestination,
                        onBackClick = {
                            if (!navController.popBackStack()) {
                                finish()
                            }
                        },
                        authApi = authApiProvider,
                    )
                }
            }
        }
    }

    companion object {
        const val START_DESTINATION = "start_destination"
    }
}