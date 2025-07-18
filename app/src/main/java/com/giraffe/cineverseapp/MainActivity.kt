package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.giraffe.designsystem.composable.custom.Button
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.details.DetailsApi
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val detailsApi: DetailsApi by inject()
            CineVerseTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        onClick = {
                            detailsApi.navigateToMovieDetails(268)
                        },
                    ) {
                        Text(text = "Navigate to movie details")
                    }
                }
            }
        }
    }
}

