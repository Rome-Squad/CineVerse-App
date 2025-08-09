package com.giraffe.presentation.home.components

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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giraffe.presentation.home.screen.show_more.PosterUiState
import com.giraffe.presentation.home.screen.home.MediaType


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TransitionLazyColumnToGrid(
    poster: List<PosterUiState>,
    isListSelected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    onScroll: (isScrollingUp: Boolean) -> Unit = {},
    onClickItem: (id: Int, mediaType: MediaType) -> Unit = { _, _ -> }
) {
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()

    ObserveScrollDirection(listState, onScroll)
    ObserveScrollDirection(gridState, onScroll)

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
        ) { isListView ->
            if (isListView) {
                PosterListView(
                    poster = poster,
                    listState = listState,
                    contentPadding = contentPadding,
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    onClickItem = onClickItem
                )
            } else {
                PosterGridView(
                    poster = poster,
                    gridState = gridState,
                    contentPadding = contentPadding,
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    onClickItem = onClickItem
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PosterListView(
    poster: List<PosterUiState>,
    listState: androidx.compose.foundation.lazy.LazyListState,
    contentPadding: PaddingValues,
    animatedVisibilityScope: androidx.compose.animation.AnimatedVisibilityScope,
    sharedTransitionScope: androidx.compose.animation.SharedTransitionScope,
    onClickItem: (id: Int, mediaType: MediaType) -> Unit
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(items = poster, key = { poster -> poster.id }) { poster ->
            PosterHorizontal(
                poster = poster,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionScope = sharedTransitionScope,
                onClick = { onClickItem(poster.id, poster.mediaType) }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PosterGridView(
    poster: List<PosterUiState>,
    gridState: androidx.compose.foundation.lazy.grid.LazyGridState,
    contentPadding: PaddingValues,
    animatedVisibilityScope: androidx.compose.animation.AnimatedVisibilityScope,
    sharedTransitionScope: androidx.compose.animation.SharedTransitionScope,
    onClickItem: (id: Int, mediaType: MediaType) -> Unit
) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(items = poster, key = { poster -> poster.id }) { poster ->
            PosterVertically(
                poster = poster,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionScope = sharedTransitionScope,
                onClick = { onClickItem(poster.id, poster.mediaType) }
            )
        }
    }
}

@Composable
fun ObserveScrollDirection(
    listState: androidx.compose.foundation.lazy.LazyListState,
    onScroll: (isScrollingUp: Boolean) -> Unit
) {
    LaunchedEffect(listState) {
        var previousIndex = listState.firstVisibleItemIndex
        var previousScrollOffset = listState.firstVisibleItemScrollOffset
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                if (index > previousIndex || (index == previousIndex && offset > previousScrollOffset)) {
                    onScroll(false)
                } else if (index < previousIndex || offset < previousScrollOffset) {
                    onScroll(true)
                }
                previousIndex = index
                previousScrollOffset = offset
            }
    }
}

@Composable
fun ObserveScrollDirection(
    gridState: androidx.compose.foundation.lazy.grid.LazyGridState,
    onScroll: (isScrollingUp: Boolean) -> Unit
) {
    LaunchedEffect(gridState) {
        var previousIndex = gridState.firstVisibleItemIndex
        var previousScrollOffset = gridState.firstVisibleItemScrollOffset
        snapshotFlow { gridState.firstVisibleItemIndex to gridState.firstVisibleItemScrollOffset }
            .collect { (index, offset) ->
                if (index > previousIndex || (index == previousIndex && offset > previousScrollOffset)) {
                    onScroll(false)
                } else if (index < previousIndex || offset < previousScrollOffset) {
                    onScroll(true)
                }
                previousIndex = index
                previousScrollOffset = offset
            }
    }
}