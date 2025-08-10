package com.giraffe.presentation.explore.util

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BasePagingSource<T : Any>(
    private val error: (Throwable) -> Unit = {},
    private val dataSource: suspend (Int) -> List<T>
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: 1
            val result = dataSource(nextPage)
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = if (result.isEmpty()) null else nextPage.inc()
            )
        } catch (e: Exception) {
            error(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>) = state.anchorPosition
}