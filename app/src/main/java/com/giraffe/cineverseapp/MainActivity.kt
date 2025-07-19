package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.navigation.DetailsApiImp
import com.giraffe.explore.navigation.ExploreApiImp


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
                AppNavigation(
                    exploreApi = ExploreApiImp(),
                    detailsApi = DetailsApiImp()
                )
            }
        }
    }
}