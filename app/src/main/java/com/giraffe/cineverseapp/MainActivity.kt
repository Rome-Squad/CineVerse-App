package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.cineverseapp.ui.theme.CineVerseAppTheme
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.islamicimageviewer.IslamicAppropriateImageViewer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = Theme.color.background.screen
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Theme.color.background.screen)
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IslamicAppropriateImageViewer(
                            imageUrl = "https://cdn.prod.website-files.com/6009ec8cda7f305645c9d91b/66a4263d01a185d5ea22eec6_6408f35499e9a3dfbab1a22f_phantom%2520of%2520the%2520opera-min.png",
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CineVerseAppTheme {
        Greeting("Android")
    }
}