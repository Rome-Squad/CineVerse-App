package com.giraffe.details.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun RatingStars(
    modifier: Modifier = Modifier,
    starSize: Dp = 24.dp,
    starSpace: Dp = 8.dp,
    rate: Int = 0,
    onRateClickEnabled: Boolean = true,
    onRateClick: (Int) -> Unit = {}
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(starSpace),
        verticalAlignment = Alignment.CenterVertically
    ) {

        for (i in 0 until 5) {

            val isSelected = i < rate
            Icon(
                painter = painterResource(

                id = if (isSelected) R.drawable.bold_star
                else R.drawable.outline_star

            ),
                tint = if (isSelected) Theme.color.additional.primary.yellow
                else Theme.color.shade.tertiary,

                contentDescription = stringResource(R.string.rate, rate),
                modifier = Modifier
                    .size(starSize)
                    .then(

                        if (onRateClickEnabled) Modifier.clickable { onRateClick(i + 1) }
                        else Modifier

                    )

            )

        }

    }

}


@Composable
@Preview(
    showBackground = true, showSystemUi = true
)
fun PreviewRatingStars() {

    var rate by remember {
        mutableIntStateOf(
            0
        )
    }

    CineVerseTheme(
        isDarkTheme = true
    ) {
        RatingStars(
            rate = rate
        ) {
            rate = it
        }
    }
}

@Composable
@Preview(
    showBackground = true, showSystemUi = false
)
fun PreviewRatingStarsNight() {

    var rate by remember {
        mutableIntStateOf(
            0
        )
    }

    CineVerseTheme(
        isDarkTheme = false
    ) {
        RatingStars(
            rate = rate
        ) {
            rate = it
        }
    }
}