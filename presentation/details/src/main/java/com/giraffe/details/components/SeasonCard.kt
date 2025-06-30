package com.giraffe.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme

@Composable
fun SeasonCard(
    modifier: Modifier = Modifier,
    poster: Painter,
    title: String,
    caption: String,
    rating: Double,
    episodes: Int,
    year: Int,
    posterWidth: Dp = 48.dp,
    posterHeight: Dp = 64.dp,
    ratingIcon: Painter = painterResource(id = Theme.icons.dueTone.star),
    episodesIcon: Painter = painterResource(id = Theme.icons.dueTone.videoLibrary),
    calendarIcon: Painter = painterResource(id = Theme.icons.dueTone.calendar),
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Theme.color.background.card)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = poster,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = posterWidth, height = posterHeight)
                        .clip(
                            RoundedCornerShape(
                                topStart = Theme.radius.x4l,
                                topEnd = Theme.radius.x4l,
                                bottomEnd = Theme.radius.xs,
                                bottomStart = Theme.radius.xs
                            )
                        ),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier=Modifier.padding(bottom = 4.dp),
                        text = title,
                        style = Theme.textStyle.body.md.medium,
                        color = Theme.color.shade.primary
                    )
                    Text(
                        text = caption,
                        style = Theme.textStyle.body.sm.regular,
                        color = Theme.color.shade.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(thickness = 0.5.dp, color = Theme.color.stroke.primary)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = ratingIcon,
                    tint = Theme.color.additional.primary.yellow,
                    contentDescription = "Rating",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = rating.toString(),
                    color = Theme.color.shade.secondary,
                    style = Theme.textStyle.label.md.regular,
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    painter = episodesIcon,
                    contentDescription = "Episodes",
                    tint = Theme.color.shade.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$episodes Episodes",
                    color = Theme.color.shade.secondary,
                    style = Theme.textStyle.label.md.regular,
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    painter = calendarIcon,
                    contentDescription = "Year",
                    tint = Theme.color.shade.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$year",
                    color = Theme.color.shade.secondary,
                    style = Theme.textStyle.label.md.regular,
                )
            }
        }
    }
}
@Preview(
    name = "SeasonCard Dark",
    apiLevel = 34,
    showBackground = true,
    showSystemUi = false
)
@Composable
fun PreviewSeasonCardDark() {
    CineVerseTheme(isDarkTheme = true) {
        SeasonCard(
            poster = painterResource(
                id = 0 // todo
            ),
            title = "Season",
            caption = "Caption",
            rating = 7.5,
            episodes = 20,
            year = 2019
        )
    }
}

@Preview(
    name = "SeasonCard Light",
    apiLevel = 34,
    showBackground = true,
    showSystemUi = false
)
@Composable
fun PreviewSeasonCardLight() {
    CineVerseTheme(isDarkTheme = false) {
        SeasonCard(
            poster = painterResource(
                id = 0 // todo
            ),
            title = "Season",
            caption = "Caption",
            rating = 7.5,
            episodes = 20,
            year = 2019
        )
    }
}
