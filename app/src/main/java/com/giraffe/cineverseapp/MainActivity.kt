package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import com.giraffe.home.navigation.HomeApiImp
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    val detailsApi: DetailsApi by inject()
    val exploreApi: ExploreApi by inject()
    val homeApi: HomeApiImp = HomeApiImp(detailsApi)

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
                homeApi.HomeContainer()
            }
        }
    }
}