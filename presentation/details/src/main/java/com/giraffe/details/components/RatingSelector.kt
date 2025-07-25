package com.giraffe.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme

@Composable
fun RatingSelector(
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    rate: Int = 0,
    onRateClick: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RatingEmojiState(
            modifier = Modifier,
            iconSize = iconSize,
            rate = rate
        )

        RatingStars(
            modifier = Modifier,
            starSize = iconSize,
            rate = rate,
            onRateClickEnabled = true,
            onRateClick = onRateClick
        )
    }

}


@Composable
@Preview(
    showBackground = true, showSystemUi = false
)
fun PreviewRatingSelector() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        RatingSelector(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true, showSystemUi = false
)
fun PreviewRatingSelectorDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        RatingSelector(
            modifier = Modifier
        )
    }
}
