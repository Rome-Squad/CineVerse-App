package com.giraffe.presentation.explore.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
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

@Composable
fun TransitionLazyColumnToGrid(
    modifier: Modifier = Modifier,
    posters: LazyPagingItems<Poster>,
    isListSelected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(16.dp),
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


    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Adaptive(minSize = if (isListSelected) 328.dp else 156.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(posters.itemCount, key = { posters[it]?.id!! }) { index ->
            posters[index]?.let { poster ->
                PosterVertically(
                    poster = poster,
                    isGridSelected = !isListSelected,
                    onClick = {
                        onPosterClicked(poster.id)
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