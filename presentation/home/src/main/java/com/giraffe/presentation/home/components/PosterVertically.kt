package com.giraffe.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.giraffe.designsystem.composable.Rating
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.Theme
import com.giraffe.imageviewer.component.SafeIslamicImage
import com.giraffe.presentation.home.model.PosterUiModel


@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PosterVertically(
    poster: PosterUiModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        with(sharedTransitionScope) {

            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(Theme.radius.lg))
                    .background(Theme.color.background.card)
                    .clickable(onClick = dropUnlessResumed { onClick() })
                    .aspectRatio(0.74f),
                contentAlignment = Alignment.Center
            ) {
                SafeIslamicImage(
                    imageUrl = poster.imageUri,
                    contentDescription = poster.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "image - ${poster.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
                Rating(
                    value = poster.rating,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp, top = 8.dp)
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "rate - ${poster.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )

                )
            }


            Text(
                text = poster.name,
                style = Theme.textStyle.body.md.medium,
                color = Theme.color.shade.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.sharedElement(
                    sharedContentState = rememberSharedContentState(key = "name - ${poster.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )
        }
    }
}