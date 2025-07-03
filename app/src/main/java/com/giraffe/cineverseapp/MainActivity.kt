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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
                            .verticalScroll(rememberScrollState())
                            .background(Theme.color.background.screen)
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IslamicAppropriateImageViewer(
                            imageUrl = "https://cdn.prod.website-files.com/6009ec8cda7f305645c9d91b/66a4263d01a185d5ea22eec6_6408f35499e9a3dfbab1a22f_phantom%2520of%2520the%2520opera-min.png",
                        )


                        IslamicAppropriateImageViewer(
                            imageUrl = "https://m10.hentaiera.com/029/efanp8tucz/1.webp",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
                        )
                        IslamicAppropriateImageViewer(
                            imageUrl = "https://m.media-amazon.com/images/I/71i6L1vZjiL._AC_SL1058_.jpg",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
                        )

                        IslamicAppropriateImageViewer(
                            imageUrl = "https://www.dpvintageposters.com/cgi-local/db_images/posters/cache/18508-image-1200-1200-fit.jpg",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
                        )


                        IslamicAppropriateImageViewer(
                            imageUrl = "https://m.media-amazon.com/images/I/819W7uToWVL._AC_SY879_.jpg",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
                        )


                        IslamicAppropriateImageViewer(
                            imageUrl = "https://i.ebayimg.com/images/g/DLQAAOSwhVplcyOd/s-l1600.webp",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
                        )


                        IslamicAppropriateImageViewer(
                            imageUrl = "https://m.media-amazon.com/images/I/71KPOvu-hOL._AC_SL1351_.jpg",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
                        )

                        IslamicAppropriateImageViewer(
                            imageUrl = "https://i.ebayimg.com/images/g/GhoAAOSwmShi46Do/s-l1600.webp",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
                        )

                        IslamicAppropriateImageViewer(
                            imageUrl = "https://curiosityhuman.com/wp-content/uploads/2016/09/Aftermath-Best-Porn-Movies.jpg",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
                        )

                        IslamicAppropriateImageViewer(
                            imageUrl = "https://i.ebayimg.com/images/g/y9gAAOSwUQhi-9Nq/s-l1600.webp",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(innerPadding)
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