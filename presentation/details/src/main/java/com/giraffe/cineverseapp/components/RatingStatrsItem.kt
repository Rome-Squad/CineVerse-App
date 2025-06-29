package com.giraffe.cineverseapp.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.R
import com.giraffe.presentation.designsystem.theme.CinVerseTheme
import com.giraffe.presentation.designsystem.theme.Theme


@Composable
fun RatingStatrs(
    modifier: Modifier = Modifier,
    rate: Int = 0,
    onRateClickEnabled: Boolean = true,
    onRateClick: (Int) -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 5) {

            Icon(
                painter = painterResource(

                    id = if (i < rate)
                        R.drawable.bold_star
                    else
                        R.drawable.outline_star

                ),
                tint = Theme.color.additional.primary.yellow,
                contentDescription = "rate $rate",
                modifier = Modifier
                    .then(

                        if (onRateClickEnabled)
                            Modifier.clickable { onRateClick(i + 1) }
                        else
                            Modifier

                    )

            )

        }

    }

}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun PreviewRatingStars() {

    var rate by remember {
        mutableIntStateOf(
            0
        )
    }

    CinVerseTheme(
        isDarkTheme = true
    ) {
        RatingStatrs(
            rate = rate
        ) {
            rate = it
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewRatingStarsNight() {

    var rate by remember {
        mutableIntStateOf(
            0
        )
    }

    CinVerseTheme(
        isDarkTheme = false
    ) {
        RatingStatrs(
            rate = rate
        ) {
            rate = it
        }
    }
}
