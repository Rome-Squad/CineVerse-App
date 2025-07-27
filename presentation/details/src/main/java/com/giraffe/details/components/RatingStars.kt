package com.giraffe.details.components


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme


@Composable
fun RatingStars(
    modifier: Modifier = Modifier,
    starSize: Dp = 24.dp,
    starSpace: Dp = 8.dp,
    rate: Int = 0,
    onRateClick: ((Int) -> Unit)? = null
) {
    val onClickEnabled = onRateClick != null
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(starSpace),
        verticalAlignment = Alignment.CenterVertically
    ) {

        for (i in 0 until 5) {

            val isSelected = i < rate
            val iconColor: Color by animateColorAsState(
                if (isSelected) Theme.color.additional.primary.yellow
                else Theme.color.shade.tertiary
            )
            Icon(
                painter = painterResource(

                    id = if (isSelected) R.drawable.bold_star
                    else R.drawable.outline_star

                ),
                tint = iconColor,

                contentDescription = stringResource(R.string.rate, rate),
                modifier = Modifier
                    .size(starSize)
                    .noHoverClickable {
                        if (onClickEnabled)
                            onRateClick(i + 1)
                        else null
                    }

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