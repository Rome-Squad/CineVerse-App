package com.giraffe.details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.CustomCard
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R

@Composable
fun SeasonCard(
    poster: String?,
    title: String,
    overview: String,
    rating: Float,
    episodes: Int,
    year: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    posterWidth: Dp = 48.dp,
    ratingIcon: Painter = painterResource(id = Theme.icons.dueTone.star),
    episodesIcon: Painter = painterResource(id = Theme.icons.dueTone.videoLibrary),
    calendarIcon: Painter = painterResource(id = Theme.icons.dueTone.calendar),
) {
    CustomCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = Theme.color.background.card,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .width(posterWidth)
                        .fillMaxHeight()
                        .height(64.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = Theme.radius.x4l,
                                topEnd = Theme.radius.x4l,
                                bottomEnd = Theme.radius.xs,
                                bottomStart = Theme.radius.xs
                            )
                        )
                ) {
//                    Image(
//                        painter = painterResource(poster.toString().toInt()),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.fillMaxSize()
//                    )
                }

                Column {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = title,
                        style = Theme.textStyle.body.md.medium,
                        color = Theme.color.shade.primary
                    )
                    AnimatedVisibility(overview.isNotBlank()) {
                        Text(
                            text = overview,
                            style = Theme.textStyle.body.sm.regular,
                            color = Theme.color.shade.secondary,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Box(modifier = Modifier
                .background(Theme.color.stroke.primary)
                .height(1.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = ratingIcon,
                    tint = Theme.color.additional.primary.yellow,
                    contentDescription = stringResource(R.string.rating),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = rating.toString(),
                    color = Theme.color.shade.secondary,
                    style = Theme.textStyle.label.md.regular,
                )
                Icon(
                    painter = episodesIcon,
                    contentDescription = stringResource(R.string.episodes),
                    tint = Theme.color.shade.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = "$episodes ${stringResource(R.string.episodes)}",
                    color = Theme.color.shade.secondary,
                    style = Theme.textStyle.label.md.regular,
                )

                Icon(
                    painter = calendarIcon,
                    contentDescription = stringResource(R.string.year),
                    tint = Theme.color.shade.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "$year",
                    color = Theme.color.shade.secondary,
                    style = Theme.textStyle.label.md.regular,
                )
            }
        }
    }
}