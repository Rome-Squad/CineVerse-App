package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.explore.screen.ExploreScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
                ExploreScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                )
            }
        }
    }
}
