package com.giraffe.presentation.details.screens.recommended.movie

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.giraffe.presentation.details.components.PosterHorizontal
import com.giraffe.presentation.details.components.PosterVertically
import com.giraffe.presentation.details.models.MovieUi
import com.giraffe.presentation.details.models.toPoster
import com.giraffe.presentation.details.utils.ObserveScrollDirection

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TransitionLazyColumnToGridMovie(
    lazyPagingItems: LazyPagingItems<MovieUi>,
    isListSelected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    onScroll: (isScrollingUp: Boolean) -> Unit = {},
    onItemClick: (Int) -> Unit,
    ) {
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()

    ObserveScrollDirection(
        listState,
        { it.firstVisibleItemIndex },
        { it.firstVisibleItemScrollOffset },
        onScroll
    )
    ObserveScrollDirection(
        gridState,
        { it.firstVisibleItemIndex },
        { it.firstVisibleItemScrollOffset },
        onScroll
    )

    SharedTransitionLayout {
        AnimatedContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            targetState = isListSelected,
            label = "ViewToggleAnimation",
            transitionSpec = {
                (fadeIn(animationSpec = tween(220, delayMillis = 90, easing = EaseIn)) +
                        scaleIn(
                            initialScale = 0.92f,
                            animationSpec = tween(220, delayMillis = 90, easing = EaseIn)
                        ))
                    .togetherWith(fadeOut(animationSpec = tween(90, easing = EaseOut)))
            }
        ) { isList ->
            val animatedScope = this
            if (isList) {
                MovieListView(
                    lazyPagingItems = lazyPagingItems,
                    listState = listState,
                    contentPadding = contentPadding,
                    onItemClick = { movie -> onItemClick(movie.id) },
                    animatedContentScope = animatedScope,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            } else {
                MovieGridView(
                    lazyPagingItems = lazyPagingItems,
                    gridState = gridState,
                    contentPadding = contentPadding,
                    onItemClick = { movie -> onItemClick(movie.id) },
                    animatedContentScope = animatedScope,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MovieListView(
    lazyPagingItems: LazyPagingItems<MovieUi>,
    listState: LazyListState,
    contentPadding: PaddingValues,
    onItemClick: (MovieUi) -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { poster ->
                PosterHorizontal(
                    poster = poster.toPoster(),
                    animatedVisibilityScope = animatedContentScope,
                    sharedTransitionScope = sharedTransitionScope,
                    onClick = { onItemClick(poster) }
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MovieGridView(
    lazyPagingItems: LazyPagingItems<MovieUi>,
    gridState: LazyGridState,
    contentPadding: PaddingValues,
    onItemClick: (MovieUi) -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(165.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { poster ->
                PosterVertically(
                    poster = poster.toPoster(),
                    animatedVisibilityScope = animatedContentScope,
                    sharedTransitionScope = sharedTransitionScope,
                    onClick = { onItemClick(poster) }
                )
            }
        }
    }
}