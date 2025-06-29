package com.giraffe.cineverseapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.R
import com.giraffe.presentation.designsystem.theme.CinVerseTheme
import com.giraffe.presentation.designsystem.theme.Theme

@Composable
fun RatingSection(
    modifier: Modifier = Modifier,
    rate: Int? = null,
    onArrowButtonClick: () -> Unit = {}
) {
    val rated = !(rate == null || rate == 0)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = stringResource(
                id = R.string.did_you_watch_it
            ),
            style = Theme.textStyle.title.sm,
            color = Theme.color.shade.primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        size = Theme.radius.lg
                    )
                )
                .background(
                    color = Theme.color.background.card
                )
                .padding(
                    all = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                size = Theme.radius.full
                            )
                        )
                        .background(
                            color = Theme.color.shade.quinary ?: Color(0xFF242533)
                        )
                        .padding(
                            all = 12.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.due_tone_star
                        ),
                        contentDescription = stringResource(
                            id = R.string.rate
                        ),
                        tint = Theme.color.brand.primary
                    )
                }

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(
                                id = if (rated)
                                    R.string.you_rated_it
                                else
                                    R.string.give_it_start
                            ),
                            style = Theme.textStyle.title.sm,
                            color = Theme.color.shade.primary
                        )

                        if (rated) {
                            RatingStars(
                                rate = rate,
                                starSize = 16.dp,
                                onRateClickEnabled = false
                            )
                        }

                    }


                    Text(
                        text = stringResource(
                            id = if (rated)
                                R.string.tab_to_change_your_rating
                            else
                                R.string.rate_it
                        ),
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.color.shade.secondary
                    )

                }

            }

            Icon(
                painter = painterResource(
                    id = R.drawable.outline_alt_arrow_right
                ),
                contentDescription = stringResource(
                    id = R.string.right_arrow
                ),
                tint = Theme.color.shade.tertiary ?: Color(0xFF72727B),
                modifier = Modifier
                    .clickable {
                        onArrowButtonClick()
                    }
            )
        }

    }

}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewRatingSection() {

    CinVerseTheme(
        isDarkTheme = false
    ) {
        RatingSection(
            modifier = Modifier
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = false
)
fun PreviewRatingSectionDark() {

    CinVerseTheme(
        isDarkTheme = true
    ) {
        RatingSection(
            modifier = Modifier
        )
    }
}
