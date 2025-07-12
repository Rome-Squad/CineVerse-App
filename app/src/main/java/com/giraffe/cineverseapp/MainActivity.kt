package com.giraffe.cineverseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.explore.screen.SearchScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineVerseTheme {
//                LaunchedEffect(Unit) {
//                    val keywords = getKeywords.execute("Batman")
//                    Log.d("TAG", "onCreate: $keywords")
//                }
                SearchScreen()
            }
        }
    }
}
