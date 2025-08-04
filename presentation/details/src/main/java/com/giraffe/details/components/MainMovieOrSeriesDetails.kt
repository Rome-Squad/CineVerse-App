package com.giraffe.details.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.imageviewer.component.SafeIslamicImage


@Composable
fun MainMovieOrSeriesDetails(
    type: String,
    posterUrl: String?,
    name: String,
    genres: List<String>,
    rating: Float,
    duration: String?,
    releaseDate: String,
    isPlayButtonEnabled: Boolean,
    onClickPlay: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier,
    textTopPadding: Dp = 16.dp,
    imageWidth: Dp = 216.dp,
    imageHeight: Dp = 289.dp,
    startAndBottomPadding: Dp
) {
    val playButtonBackground by animateColorAsState(
        if (isPlayButtonEnabled) Theme.color.button.primary else Theme.color.button.onDisabled
    )

    val imageAnimationProgress = remember(imageHeight) {
        -1f + (imageHeight - 40.dp) / (249.dp)
    }

    val imageClipRadius = Theme.radius.xl

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        posterUrl?.let {
            SafeIslamicImage(
                imageUrl = it,
                contentDescription = stringResource(R.string.poster_image),
                modifier = Modifier
                    .align(BiasAlignment(imageAnimationProgress, -1f))
                    .padding(bottom = textTopPadding)
                    .size(width = imageWidth, height = imageHeight)
                    .clip(RoundedCornerShape(imageClipRadius + (40.dp - imageClipRadius) * (imageAnimationProgress * -1))),
                contentScale = ContentScale.Crop
            )
            {
                Icon(
                    painter = painterResource(Theme.icons.dueTone.image),
                    contentDescription = null,
                    tint = Theme.color.brand.secondary,
                    modifier = Modifier
                        .size(width = 216.dp, height = 289.dp)
                        .clip(RoundedCornerShape(Theme.radius.xl))
                        .background(
                            Theme.color.background.card,
                        )
                        .padding(horizontal = 92.dp, vertical = 128.5.dp)
                        .wrapContentSize(),
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = (289.dp + textTopPadding) * (1f + imageAnimationProgress))
                    .fillMaxWidth()
                    .heightIn(min = 52.dp)
                    .clip(RoundedCornerShape(Theme.radius.lg))
                    .background(Theme.color.background.card.copy(1f + imageAnimationProgress))
                    .padding(
                        start = startAndBottomPadding,
                        bottom = startAndBottomPadding,
                        end = 16.dp * (1f + imageAnimationProgress)
                    )
                    .align(Alignment.TopEnd)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 36.dp * imageAnimationProgress * -1f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 48.dp)
                    ) {
                        if (textTopPadding == 16.dp) {
                            Text(
                                text = type.uppercase(),
                                style = Theme.textStyle.label.md.medium,
                                color = Theme.color.brand.primary,
                                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                            )
                        }

                        Text(
                            text = name,
                            style = Theme.textStyle.title.md,
                            color = Theme.color.shade.primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.then(
                                if (imageHeight == 40.dp) Modifier.padding(top = 9.dp)
                                else Modifier
                            )
                        )

                        if (genres.isNotEmpty() && textTopPadding == 16.dp) {
                            Text(
                                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp),
                                text = genres.joinToString(", "),
                                style = Theme.textStyle.body.sm.medium,
                                color = Theme.color.shade.secondary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    if (
                        (rating != 0f || !duration.isNullOrEmpty() || releaseDate.isNotEmpty())
                        && textTopPadding == 16.dp
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (rating != 0f && textTopPadding == 16.dp) {
                                IconWithText(
                                    icon = painterResource(Theme.icons.dueTone.star),
                                    text = "%.1f".format(rating),
                                    colorOfIcon = Theme.color.additional.primary.yellow
                                )
                            }

                            if (!duration.isNullOrEmpty() && textTopPadding == 16.dp) {
                                IconWithText(
                                    icon = painterResource(Theme.icons.dueTone.clock),
                                    text = duration.toString(),
                                    colorOfIcon = Theme.color.shade.secondary
                                )
                            }

                            if (releaseDate.isNotEmpty() && textTopPadding == 16.dp) {
                                IconWithText(
                                    icon = painterResource(Theme.icons.dueTone.calendar),
                                    text = releaseDate,
                                    colorOfIcon = Theme.color.shade.secondary
                                )
                            }
                        }
                    }
                }


                Box(
                    modifier = Modifier
//                        .padding(top = if (imageHeight != 40.dp) 17.dp else 0.dp)
//                        .align(Alignment.CenterEnd)
                        .width(40.dp + (88.dp - 40.dp) * imageAnimationProgress * -1)
                        .height(88.dp - (88.dp - 40.dp) * imageAnimationProgress * -1)
                ) {
                    Icon(
                        painter = painterResource(Theme.icons.dueTone.play),
                        contentDescription = stringResource(R.string.play_icon),
                        tint = Theme.color.brand.tertiary,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(Theme.radius.md))
                            .background(
                                color = playButtonBackground,
                                shape = RoundedCornerShape(Theme.radius.md)
                            )
                            .clickable(
                                enabled = isPlayButtonEnabled,
                                onClick = onClickPlay
                            )
                            .padding(10.dp)
                            .align(Alignment.TopEnd)
                    )

                    Icon(
                        painter = painterResource(Theme.icons.dueTone.add),
                        contentDescription = stringResource(R.string.save_Icon),
                        tint = Theme.color.shade.primary,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(Theme.radius.md))
                            .background(
                                color = Theme.color.button.secondary,
                                shape = RoundedCornerShape(Theme.radius.md)
                            )
                            .clickable(onClick = onClickAdd)
                            .padding(10.dp)
                            .align(Alignment.BottomStart)
                    )
                }
            }
//            }
        }

    }

}


@Composable
private fun IconWithText(
    text: String,
    icon: Painter,
    colorOfIcon: Color,
    modifier: Modifier = Modifier,
    spaceBetweenIconAndText: Dp = 4.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spaceBetweenIconAndText)
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.icon),
            modifier = Modifier.size(16.dp),
            tint = colorOfIcon
        )
        Text(
            text = text,
            style = Theme.textStyle.label.md.regular,
            color = Theme.color.shade.secondary,
            minLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}