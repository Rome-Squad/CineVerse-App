package com.giraffe.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.CustomCard
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.utils.toLocalizedRating

@Composable
fun SeasonCard(
    posterUrl: String?,
    title: String,
    overview: String,
    rating: Float,
    episodes: Int,
    year: Int?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    posterWidth: Dp = 48.dp,
    ratingIcon: Painter = painterResource(id = Theme.icons.dueTone.star),
    episodesIcon: Painter = painterResource(id = Theme.icons.dueTone.videoLibrary),
    calendarIcon: Painter = painterResource(id = Theme.icons.dueTone.calendar),
) {
    CustomCard(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.lg))
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            ),
        shape = RoundedCornerShape(Theme.radius.lg),
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
            ) {

                Box(
                    modifier = Modifier
                        .width(posterWidth)
                        .heightIn(min = 64.dp)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                topStart = Theme.radius.x4l,
                                topEnd = Theme.radius.x4l,
                                bottomEnd = Theme.radius.xs,
                                bottomStart = Theme.radius.xs
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    posterUrl?.let {
                        SafeIslamicImage(
                            imageUrl = it,
                            contentDescription = stringResource(R.string.season_image),
                            modifier = Modifier.fillMaxSize(),
                            hasSensitiveText = false,
                            contentScale = ContentScale.Crop,
                            placeHolderTint = Theme.color.brand.secondary,
                            placeholderModifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 1.dp,
                                    color = Theme.color.stroke.primary,
                                    shape = RoundedCornerShape(
                                        topStart = Theme.radius.x4l,
                                        topEnd = Theme.radius.x4l,
                                        bottomEnd = Theme.radius.xs,
                                        bottomStart = Theme.radius.xs
                                    )
                                )
                        )
                    }
                }
                Column(
                    modifier = Modifier.heightIn(min = 64.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (title.isNotBlank()) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = title,
                            style = Theme.textStyle.body.md.medium,
                            color = Theme.color.shade.primary
                        )
                    }
                    if (overview.isNotBlank()) {
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

            Box(
                modifier = Modifier
                    .background(Theme.color.stroke.primary)
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
            ) {
                if (rating > 0f) {
                    Icon(
                        painter = ratingIcon,
                        tint = Theme.color.additional.primary.yellow,
                        contentDescription = stringResource(R.string.rating),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = rating.toLocalizedRating(),
                        color = Theme.color.shade.secondary,
                        style = Theme.textStyle.label.md.regular,
                    )
                }

                if (episodes != 0) {
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
                }

                if (year != null) {
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
}

@Preview
@Composable
private fun Preview() {
    CineVerseTheme(isDarkTheme = true) {
        SeasonCard(
            posterUrl = "data:image/jpeg;base64,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",
            title = "Season 1",
//            overview = "After being bitten by a genetically-modified spider, a shy teenager gains spider-like abilities that he uses to fight injustice as a masked superhero and face a vengeful enemy.",
            overview = "dsdafkadssfll",
            rating = 0.4f,
            episodes = 15,
            year = 2002,
        )
    }

}