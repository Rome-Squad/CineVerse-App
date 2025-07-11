package com.giraffe.cineverseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.movie.datasource.remote.MoviesRemoteDataSource
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

val remote: MoviesRemoteDataSource by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
                LaunchedEffect(Unit) {
                    //val movie = remote.getMovieById(268)
                    val movie = remote.getMoviesByName("Batman")
                    Log.d("TAG", "onCreate: $movie")
                }
            }
        }
    }
}
