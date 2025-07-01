package com.giraffe.match.utils

import androidx.compose.foundation.lazy.LazyListState
import kotlin.math.abs


fun calculateCenterItem(state: LazyListState): Int {
    val layoutInfo = state.layoutInfo
    val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2

    var closestItemIndex = state.firstVisibleItemIndex
    var minDistance = Int.MAX_VALUE

    layoutInfo.visibleItemsInfo.forEach { itemInfo ->
        val itemCenter = itemInfo.offset + itemInfo.size / 2
        val distance = abs(itemCenter - viewportCenter)
        if (distance < minDistance) {
            minDistance = distance
            closestItemIndex = itemInfo.index
        }
    }
    return closestItemIndex
}