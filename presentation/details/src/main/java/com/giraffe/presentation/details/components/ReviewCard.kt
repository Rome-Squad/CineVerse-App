package com.giraffe.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.ReadMoreText
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.presentation.details.utils.formatAsMonthDayYear
import com.giraffe.presentation.details.utils.getCurrentLocalDateTime
import kotlinx.datetime.LocalDateTime


@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    rate: Int,
    reviewText: String,
    reviewDate: LocalDateTime? = getCurrentLocalDateTime(),
    reviewerImageUrl: String?,
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
            SafeIslamicImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp),
                imageUrl = reviewerImageUrl.toString(),
                contentDescription = reviewerImageUrl.toString(),
                contentScale = ContentScale.Crop,
                placeHolderTint = Theme.color.shade.secondary,
                placeholderIcon = painterResource(id = R.drawable.profile),
                placeholderModifier = Modifier
                    .size(40.dp)
                    .border(width = 1.dp, color = Theme.color.stroke.primary, shape = CircleShape)
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

        ReadMoreText(text = reviewText)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingStars(
                rate = rate,
                starSize = 16.dp,
                starSpace = 4.dp
            )
            Text(
                text = reviewDate?.formatAsMonthDayYear() .orEmpty(),
                style = Theme.textStyle.body.sm.regular,
                color = Theme.color.shade.secondary
            )
        }
    }
}