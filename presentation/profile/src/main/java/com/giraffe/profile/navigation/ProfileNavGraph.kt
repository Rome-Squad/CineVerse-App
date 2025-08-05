package com.giraffe.profile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import com.giraffe.designsystem.theme.CineVerseTheme

@Composable
fun ProfileNavGraph(
    modifier: Modifier = Modifier
) {


}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewProfileNavGraph() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        ProfileNavGraph(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
private fun PreviewProfileNavGraphDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        ProfileNavGraph(
            modifier = Modifier
        )
    }
}
