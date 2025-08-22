package com.giraffe.presentation.home.components

import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.giraffe.presentation.home.model.MediaType
import com.giraffe.presentation.home.model.PosterMedia
import com.giraffe.user.entity.ContentPreference

@Composable
fun TransitionLazyColumnToGrid(
    posters: LazyPagingItems<PosterMedia>,
    isListSelected: Boolean = false,
    contentPreference: ContentPreference,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onScroll: (isScrollingUp: Boolean) -> Unit = {},
    onClickItem: (id: Int, mediaType: MediaType) -> Unit = { _, _ -> }
) {
    val gridState = rememberLazyGridState()

    ObserveScrollDirection(gridState, onScroll)

    PosterGridView(
        posters = posters,
        gridState = gridState,
        contentPadding = contentPadding,
        isListSelected = isListSelected,
        onClickItem = onClickItem,
        contentPreference = contentPreference
    )
}

@Composable
private fun PosterGridView(
    posters: LazyPagingItems<PosterMedia>,
    gridState: androidx.compose.foundation.lazy.grid.LazyGridState,
    contentPadding: PaddingValues,
    isListSelected: Boolean,
    contentPreference: ContentPreference,
    onClickItem: (id: Int, mediaType: MediaType) -> Unit
) {
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(if (isListSelected) 328.dp else 156.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(
            count = posters.itemCount,
            key = { index -> "${posters[index]?.id} + ${posters[index]?.recentViewedAt} + $index" }
        ) { index ->
            posters[index]?.let { poster ->
                MediaPoster(
                    poster = poster,
                    isGridSelected = !isListSelected,
                    onClick = { onClickItem(poster.id, poster.mediaType) },
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .animateItem(
                            fadeInSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
                            placementSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
                            fadeOutSpec = spring(dampingRatio = 0.85f, stiffness = 100f)
                        ),
                    contentPreference = contentPreference

                )
            }
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