package com.giraffe.presentation.home.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BasePagingSource<T : Any>(
    val onError: (Throwable) -> Unit = {},
    val getData: suspend (Int) -> List<T>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: 1
        return try {
            val data = withContext(Dispatchers.IO) { getData(position) }
            LoadResult.Page(
                data = data,
                prevKey = if (position == 1) null else position,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            onError(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}