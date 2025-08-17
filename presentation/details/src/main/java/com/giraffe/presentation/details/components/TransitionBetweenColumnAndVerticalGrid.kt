package com.giraffe.presentation.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.details.R
import com.giraffe.presentation.details.components.uimodel.Poster

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TransitionBetweenColumnAndVerticalGrid(
    poster: List<Poster>,
    isListSelected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    onPosterClicked: (Int, String) -> Unit
) {
    SharedTransitionLayout {
        AnimatedContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            targetState = isListSelected,
            label = stringResource(R.string.view_toggle_animation),
            transitionSpec = {
                (fadeIn(animationSpec = tween(220, delayMillis = 90, easing = EaseIn)) +
                        scaleIn(
                            initialScale = 0.92f,
                            animationSpec = tween(220, delayMillis = 90, EaseIn)
                        ))
                    .togetherWith(fadeOut(animationSpec = tween(90, easing = EaseOut)))
            }
        ) {
            if (it) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = contentPadding
                ) {
                    items(items = poster) { poster ->
                        PosterHorizontal(
                            poster = poster,
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onClick = {
                                onPosterClicked(poster.id, poster.mediaTypeOfPoster.toString())
                            }
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(165.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = contentPadding
                ) {
                    items(items = poster) { poster ->
                        PosterVertically(
                            poster = poster,
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onClick = {
                                onPosterClicked(poster.id, poster.mediaTypeOfPoster.toString())
                            }
                        )
                    }
                }
            }
        }
    }
}