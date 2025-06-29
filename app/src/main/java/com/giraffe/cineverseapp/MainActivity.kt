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
import androidx.compose.ui.unit.dp
import com.giraffe.cineverseapp.components.ReviewCard
import com.giraffe.cineverseapp.ui.theme.CineVerseAppTheme
import com.giraffe.presentation.designsystem.theme.CinVerseTheme
import com.giraffe.presentation.designsystem.theme.Theme
import com.giraffe.presentation.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var rate by remember {
                mutableIntStateOf(
                    3
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
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {


/*

                        RatingSelector(
                            modifier = Modifier,
                            rate = rate,
                            onRateClick = {
                                rate = it
                            }
                        )
*/
                        /*
                        BSRatingContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            rate = rate,
                            onRateClick = {
                                rate = it
                            }
                        ) {
                            Log.d("TAG", "onCreate: rate added $it")
                        }*/
                        /*

                        RatingSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    all = 16.dp
                                ),
                            rate = rate,
                        ) {
                            Log.d("TAG", "onCreate: $rate")
                        }


                        RatingSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    all = 16.dp
                                ),
                            rate = null,
                        ) {

                            Log.d("TAG", "onCreate: null")
                        }
                    }
*/
/*

                        ReadMoreText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = "This has no competition. It is the very finest comic-book character movie ever made. Knowing the Burton, Donner and Nolan filmic adaptations of Batman and Superman will be a matter of history, not just of memory.",
                        )
*/
                        ReviewCard(
                            rate = 4,
                            reviewText = "A sickening, borderline fascist film that is simultaneously dull and harmful. The editing represents thatA sickening, borderline fascist film that is simultaneously dull and harmful. The editing represents that of an anti-pirate commercial and the politics are beyond repr The editing represents that of an anti-pirate commercial and the politics are beyond repr",
                            reviewerImageSource = R.drawable.reviewer,
                            reviewerName = "Bilal Azzam",
                            reviewerUsername = "bilal_azzam",
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