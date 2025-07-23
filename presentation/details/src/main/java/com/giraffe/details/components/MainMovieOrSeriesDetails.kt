package com.giraffe.details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainMovieOrSeriesDetails(
    type: String,
    posterUrl: String?,
    name: String,
    genres: List<String>,
    rating: Float,
    duration: String?,
    releaseDate: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClickPlay: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    val key = "_KEY"
    with(sharedTransitionScope) {
        Column(
            modifier = modifier.background(Theme.color.background.screen),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            posterUrl?.let {
                SafeIslamicImage(
                    imageUrl = it,
                    contentDescription = stringResource(R.string.poster_image),
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = posterUrl.toString() + key),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(width = 216.dp, height = 289.dp)
                        .clip(RoundedCornerShape(Theme.radius.xl)),
                    contentScale = ContentScale.Crop
                ) {
                    Icon(
                        painter = painterResource(Theme.icons.dueTone.image),
                        contentDescription = "",
                        tint = Theme.color.brand.secondary,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                Theme.color.background.card,
                                shape = CircleShape
                            )
                            .padding(12.dp)
                            .wrapContentSize(),
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Theme.radius.lg))
                        .background(Theme.color.background.card)
                        .padding(16.dp),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 48.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = type.uppercase(),
                                style = Theme.textStyle.label.md.medium,
                                color = Theme.color.brand.primary
                            )
                            Text(
                                text = name,
                                style = Theme.textStyle.title.md,
                                color = Theme.color.shade.primary,
                                modifier = Modifier.sharedElement(
                                    sharedContentState = rememberSharedContentState(key = name + key),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                            )
                            Text(
                                text = genres.joinToString(", "),
                                style = Theme.textStyle.body.sm.medium,
                                color = Theme.color.shade.secondary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            IconWithText(
                                icon = painterResource(Theme.icons.dueTone.star),
                                text = "%.1f".format(rating),
                                colorOfIcon = Theme.color.additional.primary.yellow
                            )
                            AnimatedVisibility(duration != null) {
                                IconWithText(
                                    icon = painterResource(Theme.icons.dueTone.clock),
                                    text = duration!!,
                                    colorOfIcon = Theme.color.shade.secondary
                                )
                            }
                            IconWithText(
                                icon = painterResource(Theme.icons.dueTone.calendar),
                                text = releaseDate,
                                colorOfIcon = Theme.color.shade.secondary
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(11.5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(Theme.icons.dueTone.play),
                            contentDescription = stringResource(R.string.play_icon),
                            tint = Theme.color.brand.tertiary,
                            modifier = Modifier
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = Theme.icons.dueTone.play.toString() + key),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                                .size(40.dp)
                                .clip(RoundedCornerShape(Theme.radius.md))
                                .background(Theme.color.button.primary)
                                .clickable(onClick = onClickPlay)
                                .padding(10.dp)
                        )
                        Icon(
                            painter = painterResource(Theme.icons.dueTone.add),
                            contentDescription = stringResource(R.string.save_Icon),
                            tint = Theme.color.shade.primary,
                            modifier = Modifier
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = Theme.icons.dueTone.add.toString() + key),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                                .size(40.dp)
                                .clip(RoundedCornerShape(Theme.radius.md))
                                .background(Theme.color.button.secondary)
                                .clickable(onClick = onClickAdd)
                                .padding(10.dp)
                        )
                    }
                }
            }
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