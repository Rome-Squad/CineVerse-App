package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.explore.screen.SearchScreen
import com.giraffe.series.repository.SeriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val seriesRepository by inject<SeriesRepository>()
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    seriesRepository.getSeriesDetails(2)
                }
            }
            CineVerseTheme {
                SearchScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                )

            }
        }
    }
}
