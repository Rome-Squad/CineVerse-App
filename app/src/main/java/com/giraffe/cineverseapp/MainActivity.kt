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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.cineverseapp.components.RatingSelector
import com.giraffe.cineverseapp.ui.theme.CineVerseAppTheme
import com.giraffe.presentation.designsystem.theme.CinVerseTheme
import com.giraffe.presentation.designsystem.theme.Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var rate by remember {
                mutableIntStateOf(
                    0
                )
            }
            CinVerseTheme {

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = Theme.color.background.screen
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Theme.color.background.screen)
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        RatingSelector(
                            modifier = Modifier,
                            rate = rate,
                            onRateClick = {
                                rate = it
                            }
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