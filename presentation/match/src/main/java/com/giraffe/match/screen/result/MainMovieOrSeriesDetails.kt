package com.giraffe.match.screen.result

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.presentation.match.R

@Composable
fun MainMovieOrSeriesDetails(
    type: String,
    name: String,
    genres: List<String>,
    rating: Float,
    duration: String?,
    releaseDate: String,
    isPlayButtonEnabled: Boolean,
    onClickPlay: () -> Unit,
    modifier: Modifier = Modifier,
    onClickAdd: (() -> Unit)? = null,
    animationProgress: Float = 0f,
) {
    val playButtonBackground by animateColorAsState(
        if (isPlayButtonEnabled) Theme.color.button.primary else Theme.color.button.onDisabled
    )

    Box(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 52.dp)
                .clip(RoundedCornerShape(Theme.radius.lg))
                .background(Theme.color.background.card.copy(1f - animationProgress))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (genres.isNotEmpty()) {
                    Text(
                        text = genres.joinToString(", "),
                        style = Theme.textStyle.body.sm.medium,
                        color = Theme.color.shade.secondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (rating != 0f) {
                        IconWithText(
                            icon = painterResource(Theme.icons.dueTone.star),
                            text = "%.1f".format(rating),
                            colorOfIcon = Theme.color.additional.primary.yellow
                        )
                    }
                    if (!duration.isNullOrBlank()) {
                        IconWithText(
                            icon = painterResource(Theme.icons.dueTone.clock),
                            text = duration,
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

            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                )

                onClickAdd?.let {
                    Icon(
                        painter = painterResource(Theme.icons.dueTone.add),
                        contentDescription = stringResource(R.string.add_Icon),
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
                    )
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
            contentDescription = stringResource(R.string.icon_description),
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