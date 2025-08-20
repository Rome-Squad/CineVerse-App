package com.giraffe.presentation.details.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.giraffe.presentation.details.components.uimodel.Poster
import com.giraffe.presentation.details.utils.ObserveScrollDirection

@Composable
fun TransitionLazyColumnToGridPoster(
    lazyPagingItems: LazyPagingItems<Poster>,
    isListSelected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onScroll: (isScrollingUp: Boolean) -> Unit = {},
    onItemClick: (Int) -> Unit,
) {
    val gridState = rememberLazyGridState()

    ObserveScrollDirection(
        gridState,
        { it.firstVisibleItemIndex },
        { it.firstVisibleItemScrollOffset },
        onScroll
    )

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(if (isListSelected) 328.dp else 156.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { poster ->
                MediaPoster(
                    poster = poster,
                    isGridSelected = !isListSelected,
                    onClick = {
                        onItemClick(poster.id)
                    },
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .animateItem(
                            fadeInSpec = tween(700, easing = LinearEasing),
                            placementSpec = tween(700, easing = LinearEasing),
                            fadeOutSpec = tween(700, easing = LinearEasing)
                        )
                )
            }
        }
    }
}