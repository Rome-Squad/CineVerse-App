package com.giraffe.cineverseapp

//import androidx.navigation.compose.rememberNavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.screens.seriesRecommendation.SeriesRecommendationScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
//                val navController = rememberNavController()
//                DetailsNavGraph(navController = navController)
//                ExploreScreen(
//                    modifier = Modifier
//                        .fillMaxSize()
//                )
                SeriesRecommendationScreen(
                    modifier = Modifier.fillMaxSize(),
                    seriesId = 1033
                )
            }
        }
    }
}