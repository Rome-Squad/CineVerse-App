package com.giraffe.explore.components

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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.giraffe.designsystem.uimodel.Poster

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TransitionLazyColumnToGrid(
    posters: LazyPagingItems<Poster>,
    isListSelected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    onScroll: (isScrollingUp: Boolean) -> Unit = {},
    onPosterClicked: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()
    LaunchedEffect(listState) {
        var previousIndex = listState.firstVisibleItemIndex
        var previousScrollOffset = listState.firstVisibleItemScrollOffset
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                if (index > previousIndex || (index == previousIndex && offset > previousScrollOffset)) {
                    onScroll(false)
                } else if (index < previousIndex || (offset < previousScrollOffset)) {
                    onScroll(true)
                }

                previousIndex = index
                previousScrollOffset = offset
            }
    }
    LaunchedEffect(gridState) {
        var previousIndex = gridState.firstVisibleItemIndex
        var previousScrollOffset = gridState.firstVisibleItemScrollOffset
        snapshotFlow { gridState.firstVisibleItemIndex to gridState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                if (index > previousIndex || (index == previousIndex && offset > previousScrollOffset)) {
                    onScroll(false)
                } else if (index < previousIndex || (offset < previousScrollOffset)) {
                    onScroll(true)
                }

                previousIndex = index
                previousScrollOffset = offset
            }
    }
    SharedTransitionLayout {
        AnimatedContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            targetState = isListSelected,
            label = "ViewToggleAnimation",
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
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = contentPadding
                ) {
                    items(posters.itemCount) { index ->
                        posters[index]?.let { poster ->
                            PosterHorizontal(
                                poster = poster,
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                onClick = {
                                    onPosterClicked(poster.id)
                                }
                            )
                        }

                    }
                }
            } else {
                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = contentPadding
                ) {
                    items(posters.itemCount) { index ->
                        posters[index]?.let { poster ->
                            PosterVertically(
                                poster = poster,
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                onClick = {
                                    onPosterClicked(poster.id)
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}