package com.giraffe.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.utils.formatAsMonthDayYear
import com.giraffe.details.utils.getCurrentLocalDate
import com.giraffe.details.utils.imageSourceToPainter
import kotlinx.datetime.LocalDate


@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    rate: Int,
    reviewText: String,
    reviewDate: LocalDate = getCurrentLocalDate(),
    reviewerImageSource: Any?,
    reviewerName: String,
    reviewerUsername: String
) {

    Column(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    size = Theme.radius.lg
                )
            )
            .background(
                color = Theme.color.background.card
            )
            .padding(
                all = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageCard(
                imageSource = reviewerImageSource, modifier = Modifier.size(40.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = reviewerName,
                    style = Theme.textStyle.body.md.medium,
                    color = Theme.color.shade.primary
                )
                Text(
                    text = stringResource(R.string.user_name, reviewerUsername),
                    style = Theme.textStyle.body.sm.regular,
                    color = Theme.color.shade.secondary
                )
            }
        }

        ReadMoreText(
            text = reviewText
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingStars(
                rate = rate, onRateClickEnabled = false, starSize = 16.dp, starSpace = 4.dp
            )

            Text(
                text = reviewDate.formatAsMonthDayYear(),
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.shade.secondary
            )

        }

    }
}


@Preview(
    showBackground = true,
)
@Composable
fun PreviewReviewCard() {

    CineVerseTheme(
        isDarkTheme = false
    ) {
        ReviewCard(
            modifier = Modifier,
            rate = 5,
            reviewText = "very good",
            reviewDate = getCurrentLocalDate(),
            reviewerImageSource = imageSourceToPainter(R.drawable.reviewer),
            reviewerName = "Hend",
            reviewerUsername = "Hend sayed",
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun PreviewReviewCardDark() {

    CineVerseTheme(
        isDarkTheme = true
    ) {
        ReviewCard(
            modifier = Modifier,
            rate = 5,
            reviewText = "very good",
            reviewDate = getCurrentLocalDate(),
            reviewerImageSource = imageSourceToPainter(R.drawable.reviewer),
            reviewerName = "Hend",
            reviewerUsername = "Hend sayed"
        )
    }
}
