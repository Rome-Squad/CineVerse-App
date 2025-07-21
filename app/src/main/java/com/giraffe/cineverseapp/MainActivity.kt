package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giraffe.cineverseapp.nav.CineVerseAppContainer
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    val detailsApi: DetailsApi by inject()
    val exploreApi: ExploreApi by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            CineVerseTheme {
                CineVerseAppContainer(
                    exploreApi = exploreApi,
                    detailsApi = detailsApi
                )
            }
        }
    }
}

