package com.giraffe.cineverseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.giraffe.cineverseapp.nav.CineVerseAppContainer
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.DetailsApi
import com.giraffe.explore.ExploreApi
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val detailsApi: DetailsApi by inject()
            val exploreApi: ExploreApi by inject()

            CineVerseTheme {
                CineVerseAppContainer(
                    exploreApi = exploreApi,
                    detailsApi = detailsApi
                )
            }
        }
    }
}

