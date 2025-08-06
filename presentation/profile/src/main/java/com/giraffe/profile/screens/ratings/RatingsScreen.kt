package com.giraffe.profile.screens.ratings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun RatingsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background.screen)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Ratings Screen",
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewRatingsScreen() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        RatingsScreen(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewRatingsScreenDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        RatingsScreen(
            modifier = Modifier
        )
    }
}
