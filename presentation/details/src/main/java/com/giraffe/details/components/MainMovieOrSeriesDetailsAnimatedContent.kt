package com.giraffe.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainMovieOrSeriesDetailsAnimatedContent(
    type: String,
    name: String,
    image: String?,
    rating: Float,
    genres: List<String>,
    releaseYear: String,
    onClickPlay: () -> Unit,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier,
    isScrolled: Boolean = false,
    duration: String? = null,
    durationAnimation: Int = 400
) {

    val topPadding by animateDpAsState(
        if (isScrolled) 0.dp else 16.dp
    )
    SharedTransitionLayout {
        AnimatedContent(
            targetState = isScrolled,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(durationAnimation)
                ) togetherWith fadeOut(animationSpec = tween(durationAnimation))
            },
            label = "Animated Content"
        ) { targetState ->
            when (targetState) {
                false -> {
                    MainMovieOrSeriesDetails(
                        type = type,
                        posterUrl = image,
                        name = name,
                        genres = genres,
                        rating = rating,
                        duration = duration,
                        releaseDate = releaseYear,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent,
                        onClickAdd = onClickAdd,
                        onClickPlay = onClickPlay,
                        modifier = modifier.padding(top = topPadding)
                    )
                }

                true -> {
                    MinimizedInfoRow(
                        posterUrl = image,
                        name = name,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedContent,
                        onClickAdd = onClickAdd,
                        onClickPlay = onClickPlay,
                        modifier = modifier.padding(top = topPadding)
                    )
                }
            }
        }
    }
}