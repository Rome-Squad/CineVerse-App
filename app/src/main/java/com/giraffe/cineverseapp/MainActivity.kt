package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.DetailsNavGraph
import com.giraffe.explore.nav.ExploreNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
                val navController = rememberNavController()
                DetailsNavGraph(navController = navController)

               // ExploreNavGraph()
            }
        }
    }
}