package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.screens.moviedetails.screen.MovieDetailsScreen
import com.giraffe.explore.ExploreScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
                MovieDetailsScreen(movieID = 268)
//                ExploreScreen(
//                    modifier = Modifier
//                        .fillMaxSize()
//                )
            }
        }
    }
}