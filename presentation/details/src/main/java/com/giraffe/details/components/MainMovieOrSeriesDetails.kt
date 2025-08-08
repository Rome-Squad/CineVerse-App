package com.giraffe.details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
    modifier: Modifier = Modifier,
    onClickAdd: (() -> Unit)? = null,
    animationProgress: Float = 0f
) {
    val playButtonBackground by animateColorAsState(
        if (isPlayButtonEnabled) Theme.color.button.primary else Theme.color.button.onDisabled
    )

    val imageClipRadius = Theme.radius.xl
    val hasSensitiveText by remember(animationProgress) {
        derivedStateOf { animationProgress < 0.5f }
    }
    Box(Modifier.fillMaxWidth()) {
        Box(modifier.fillMaxWidth()) {
            posterUrl?.let {
                SafeIslamicImage(
                    imageUrl = it,
                    hasSensitiveText = hasSensitiveText,
                    contentDescription = stringResource(R.string.poster_image),
                    modifier = Modifier
                        .align(BiasAlignment(animationProgress * -1, -1f))
                        .padding(bottom = 16.dp - (16 - 9).dp * (animationProgress))
                        .size(
                            width = 216.dp - (216 - 40).dp * (animationProgress),
                            height = 289.dp - (289 - 40).dp * (animationProgress)
                        )
                        .clip(RoundedCornerShape(imageClipRadius + (40.dp - imageClipRadius) * animationProgress)),
                    contentScale = ContentScale.Crop,
                    placeHolderTint = Theme.color.brand.secondary,
                    placeholderModifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = Theme.color.stroke.primary,
                            shape = RoundedCornerShape(
                                imageClipRadius
                                        + (40.dp - imageClipRadius)
                                        * animationProgress
                            )

                        )
                )

                Row(
                    modifier = Modifier
                        .padding(top = (289.dp + 16.dp) * ((1f - animationProgress)))
                        .fillMaxWidth()
                        .heightIn(min = 52.dp)
                        .clip(RoundedCornerShape(Theme.radius.lg))
                        .background(Theme.color.background.card.copy(1f - animationProgress))
                        .padding(
                            start = 16.dp - (16 - 12).dp * (1f - animationProgress),
                            bottom = 16.dp - (16 - 12).dp * (1f - animationProgress),
                            end = 16.dp * (1f - animationProgress)
                        )
                        .align(Alignment.TopEnd)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 36.dp * animationProgress)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 48.dp)
                        ) {
                            AnimatedVisibility(
                                visible = animationProgress == 0f,
                                enter = fadeIn(),
                                exit = fadeOut(),
                                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                            ) {
                                Text(
                                    text = type.uppercase(),
                                    style = Theme.textStyle.label.md.medium,
                                    color = Theme.color.brand.primary
                                )
                            }

                            Text(
                                text = name,
                                style = Theme.textStyle.title.md,
                                color = Theme.color.shade.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.then(
                                    if (animationProgress == 1f) Modifier.padding(top = 9.dp)
                                    else Modifier
                                )
                            )

                            AnimatedVisibility(
                                visible = genres.isNotEmpty() && animationProgress == 0f,
                                enter = fadeIn(),
                                exit = fadeOut(),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    text = genres.joinToString(", "),
                                    style = Theme.textStyle.body.sm.medium,
                                    color = Theme.color.shade.secondary,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        AnimatedVisibility(
                            visible = (rating != 0f || !duration.isNullOrEmpty()
                                    || releaseDate.isNotEmpty())
                                    && animationProgress == 0f,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                if (rating != 0f) {
                                    IconWithText(
                                        icon = painterResource(Theme.icons.dueTone.star),
                                        text = "%.1f".format(rating),
                                        colorOfIcon = Theme.color.additional.primary.yellow
                                    )
                                }

                                if (!duration.isNullOrEmpty()) {
                                    IconWithText(
                                        icon = painterResource(Theme.icons.dueTone.clock),
                                        text = duration.toString(),
                                        colorOfIcon = Theme.color.shade.secondary
                                    )
                                }

                                if (releaseDate.isNotEmpty()) {
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
                            .padding(top = 17.dp * (1f - animationProgress))
                            .width(40.dp + (88.dp - 40.dp) * animationProgress)
                            .height(92.dp - (92.dp - 40.dp) * animationProgress)
                            .align(Alignment.CenterVertically)
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

                        onClickAdd?.let {
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
                }
            }
        }

        AnimatedVisibility(
            visible = animationProgress == 1f,
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Theme.color.brand.tertiary)
            )
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