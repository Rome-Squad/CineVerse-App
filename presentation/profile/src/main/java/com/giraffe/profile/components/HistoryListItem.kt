package com.giraffe.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp
import com.giraffe.profile.screens.history.PosterUiState

@Composable
fun HistoryListItem(
    poster: List<PosterUiState>,
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    onScroll: (isScrollingUp: Boolean) -> Unit = {},
    onSwipedToLeft: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    ObserveScrollDirection(listState, onScroll)

    PosterListView(
        poster = poster,
        listState = listState,
        contentPadding = contentPadding,
        onSwiped = onSwipedToLeft
    )
}

@Composable
private fun PosterListView(
    poster: List<PosterUiState>,
    listState: LazyListState,
    contentPadding: PaddingValues,
    onSwiped: () -> Unit
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(items = poster, key = { it.id }) { poster ->
            PosterHorizontal(
                poster = poster,
                onSwiped = onSwiped
            )
        }
    }
}
@Composable
fun ObserveScrollDirection(
    listState: LazyListState,
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