package com.giraffe.details.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.details.R
import com.giraffe.imageviewer.component.SafeIslamicImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MinimizedInfoRow(
    posterUrl: String?,
    name: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClickPlay: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    val key = "_KEY"
    with(sharedTransitionScope) {
        Row(
            modifier = modifier
                .background(Theme.color.background.screen)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SafeIslamicImage(
                    imageUrl = posterUrl.toString(),
                    contentDescription = stringResource(R.string.poster_image),
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = posterUrl.toString() + key),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(40.dp)
                        .clip(RoundedCornerShape(Theme.radius.full)),
                    contentScale = ContentScale.FillBounds
                ) {
                    Icon(
                        painter = painterResource(Theme.icons.dueTone.image),
                        contentDescription = null,
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
                Text(
                    text = name,
                    style = Theme.textStyle.title.sm,
                    color = Theme.color.shade.primary,
                    modifier = Modifier.sharedElement(
                        sharedContentState = rememberSharedContentState(key = name + key),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    painter = painterResource(Theme.icons.dueTone.add),
                    contentDescription = null,
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
                Icon(
                    painter = painterResource(Theme.icons.dueTone.play),
                    contentDescription = null,
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
            }
        }
    }
}