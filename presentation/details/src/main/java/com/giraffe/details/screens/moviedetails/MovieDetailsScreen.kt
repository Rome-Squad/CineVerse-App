package com.giraffe.details.screens.moviedetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.giraffe.designsystem.theme.CineVerseTheme

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier
) {

}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewMovieDetailsScreen() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        MovieDetailsScreen(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewMovieDetailsScreenDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        MovieDetailsScreen(
            modifier = Modifier
        )
    }
}
