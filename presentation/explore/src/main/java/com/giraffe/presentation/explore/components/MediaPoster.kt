package com.giraffe.presentation.explore.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.Rating
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.presentation.explore.components.uimodel.Poster

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MediaPoster(
    poster: Poster,
    isGridSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val density = LocalDensity.current
    val widowSize = LocalWindowInfo.current
    val imageMaxWidth = with(density) {
        val windowWidth = widowSize.containerSize.width - 32.dp.toPx()
        var numberOfCards = windowWidth / 156.dp.toPx()
        val padding = (numberOfCards - 1) * 32.dp.toPx()
        numberOfCards = (windowWidth - padding) / 156.dp.toPx()
        val width = windowWidth / numberOfCards.toInt()
        width.toDp().coerceAtLeast(156.dp)
    }
    val transition = updateTransition(isGridSelected)
    val imageWidth by transition.animateDp(
        targetValueByState = { if (it) imageMaxWidth else 64.dp },
        transitionSpec = { tween(700, easing = LinearEasing) }
    )
    val columnStartPadding by transition.animateDp(
        targetValueByState = { if (it) 0.dp else 76.dp },
        transitionSpec = { tween(700, easing = LinearEasing) }
    )
    val columnEndPadding by transition.animateDp(
        targetValueByState = { if (it) 0.dp else 12.dp },
        transitionSpec = { tween(700, easing = LinearEasing) }
    )
    val verticalAlignment by transition.animateFloat(
        targetValueByState = { if (it) 1f else 0f },
        transitionSpec = { tween(700, easing = LinearEasing) }
    )

    val textHeight = with(density) { 20.sp.toDp() + 8.dp }
    val textColor by animateColorAsState(
        targetValue = if (isGridSelected) Theme.color.shade.secondary else Theme.color.shade.primary,
        animationSpec = tween(700, easing = LinearEasing)
    )

    if (poster.name.isNotBlank()) {
        Box(
            modifier = modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = Theme.radius.lg,
                        bottomStart = if (isGridSelected) Theme.radius.xxs else Theme.radius.lg,
                        topEnd = Theme.radius.lg,
                        bottomEnd = Theme.radius.lg
                    )
                )
                .then(
                    if (isGridSelected) Modifier
                    else Modifier.background(Theme.color.background.card)
                )
                .clickable(onClick = onClick)
        ) {
            SafeIslamicImage(
                imageUrl = poster.imageUrl,
                contentDescription = poster.name,
                hasSensitiveText = isGridSelected,
                placeHolderTint = Theme.color.brand.secondary,
                contentScale = ContentScale.FillBounds,
                placeholderModifier = Modifier
                    .align(Alignment.TopStart)
                    .then(
                        if (isGridSelected) Modifier.background(Theme.color.background.card)
                        else Modifier.background(color = Theme.color.brand.tertiary)
                    )
                    .width(imageWidth)
                    .aspectRatio(0.73f)
                    .heightIn(min = 88.dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(bottom = if (isGridSelected) textHeight else 0.dp)
                    .width(imageWidth)
                    .aspectRatio(0.73f)
                    .heightIn(min = 88.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg,
                            bottomEnd = if (isGridSelected) Theme.radius.lg else 0.dp
                        )
                    )
                    .background(Theme.color.background.card)
            )

            Column(
                modifier = Modifier
                    .padding(
                        start = columnStartPadding,
                        end = columnEndPadding
                    )
                    .align(BiasAlignment(-1f, verticalAlignment))
            ) {
                Text(
                    text = poster.name,
                    style = Theme.textStyle.body.md.medium,
                    color = textColor,
                    maxLines = 1,
                    modifier = Modifier.widthIn(max = 156.dp)
                )

                transition.AnimatedVisibility(
                    visible = { !it },
                    enter = fadeIn(animationSpec = tween(700, easing = LinearEasing)),
                    exit = fadeOut(animationSpec = tween(700, easing = LinearEasing)),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = poster.genres ?: stringResource(R.string.unknown_genre),
                        style = Theme.textStyle.body.sm.regular,
                        color = Theme.color.shade.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                transition.AnimatedVisibility(
                    visible = { !it },
                    enter = fadeIn(animationSpec = tween(700, easing = LinearEasing)),
                    exit = fadeOut(animationSpec = tween(700, easing = LinearEasing)),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        poster.date?.let {
                            if (!poster.time.isNullOrEmpty()) {
                                IconWithText(
                                    icon = painterResource(Theme.icons.dueTone.clock),
                                    text = poster.time
                                )
                            }

                            if (poster.date.isNotEmpty()) {
                                IconWithText(
                                    icon = painterResource(Theme.icons.dueTone.calendar),
                                    text = poster.date
                                )
                            }
                        }
                    }
                }
            }

            Rating(
                value = poster.rating,
                hasBackground = isGridSelected,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        end = 12.dp,
                        top = 12.dp
                    )
            )
        }
    }
}

@Composable
private fun IconWithText(icon: Painter, text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.clock_icon),
            tint = Theme.color.shade.secondary,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text.trim(),
            style = Theme.textStyle.label.md.regular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Theme.color.shade.secondary
        )
    }
}