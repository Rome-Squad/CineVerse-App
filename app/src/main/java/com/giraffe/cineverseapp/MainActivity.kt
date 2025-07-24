package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giraffe.authentication.AuthenticationApi
import com.giraffe.authentication.screen.LoginScreen
import com.giraffe.cineverseapp.nav.CineVerseAppContainer
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    val detailsApi: DetailsApi by inject()
    val exploreApi: ExploreApi by inject()
    val authenticationApi: AuthenticationApi by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            CineVerseTheme {
              //  LoginScreen()
                CineVerseAppContainer(
                    exploreApi = exploreApi,
                    detailsApi = detailsApi,
                    authenticationApi = authenticationApi
                )
            }
        }
    }
}

