package com.giraffe.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.giraffe.designsystem.R
import com.giraffe.designsystem.composable.Rating
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.home.screen.show_more.PosterUiState
import com.giraffe.imageviewer.component.SafeIslamicImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PosterHorizontal(
    poster: PosterUiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(Theme.radius.lg))
            .background(Theme.color.background.card)
            .clickable(onClick = dropUnlessResumed { onClick() }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        with(sharedTransitionScope) {

            SafeIslamicImage(
                imageUrl = poster.imageUri,
                contentDescription = poster.name,
                hasSensitiveText = false,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(64.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = Theme.radius.lg,
                            bottomStart = Theme.radius.lg,
                            topEnd = Theme.radius.lg
                        )
                    )
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "image - ${poster.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
            {

                Icon(
                    painter = painterResource(Theme.icons.dueTone.image),
                    contentDescription = stringResource(R.string.loading_image),
                    modifier = Modifier.size(32.dp),
                    tint = Theme.color.brand.secondary
                )

            }


            Column(
                modifier = Modifier.padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = poster.name,
                            style = Theme.textStyle.body.md.medium,
                            color = Theme.color.shade.primary,
                            maxLines = 1,
                            modifier = Modifier
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = "name - ${poster.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                        )
                        Text(
                            text = poster.genres ?: stringResource(R.string.unknown_genre),
                            style = Theme.textStyle.body.sm.regular,
                            color = Theme.color.shade.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Rating(
                        value = poster.rating,
                        modifier = Modifier
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "rate - ${poster.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    )
                }
                poster.date?.let {
                    if (it.isNotEmpty()) {
                        IconWithText(
                            icon = painterResource(Theme.icons.dueTone.calendar),
                            text = poster.date
                        )
                    }
                }

            }
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
            text = text,
            style = Theme.textStyle.label.md.regular,
            color = Theme.color.shade.secondary
        )
    }
}