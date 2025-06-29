package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.cineverseapp.components.RatingStatrs
import com.giraffe.cineverseapp.ui.theme.CineVerseAppTheme
import com.giraffe.presentation.designsystem.theme.CinVerseTheme

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

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {

                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(
                                innerPadding
                            )

                        )

                        Row {  }

                        RatingStatrs(
                            modifier = Modifier,
                            rate = rate,
                            onRateClickEnabled = true,
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