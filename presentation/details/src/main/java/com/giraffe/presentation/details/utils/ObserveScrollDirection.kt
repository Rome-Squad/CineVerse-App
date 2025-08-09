package com.giraffe.presentation.details.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> ObserveScrollDirection(
    state: T,
    getIndex: (T) -> Int,
    getOffset: (T) -> Int,
    onScroll: (isScrollingUp: Boolean) -> Unit
) {
    LaunchedEffect(state) {
        var previousIndex = getIndex(state)
        var previousOffset = getOffset(state)
        snapshotFlow { getIndex(state) to getOffset(state) }
            .collectLatest { (index, offset) ->
                if (index > previousIndex || (index == previousIndex && offset > previousOffset)) {
                    onScroll(false)
                } else if (index < previousIndex || offset < previousOffset) {
                    onScroll(true)
                }
                previousIndex = index
                previousOffset = offset
            }
    }
}