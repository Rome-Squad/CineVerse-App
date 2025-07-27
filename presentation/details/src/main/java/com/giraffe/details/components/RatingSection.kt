package com.giraffe.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.modifier.noHoverClickable
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun RatingSection(
    modifier: Modifier = Modifier,
    rate: Int? = null,
    onClickCard: () -> Unit = {}
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
                .clickable (
                    onClick = onClickCard
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
                            color = Theme.color.shade.quinary
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
                                starSize = 16.dp
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
                        style = Theme.textStyle.body.sm.medium,
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
                tint = Theme.color.shade.tertiary,
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

    CineVerseTheme(
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

    CineVerseTheme(
        isDarkTheme = true
    ) {
        RatingSection(
            modifier = Modifier
        )
    }
}
