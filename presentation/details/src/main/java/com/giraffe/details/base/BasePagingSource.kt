package com.giraffe.details.base

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BasePagingSource<DATA : Any>(
    val getData: suspend (Int) -> List<DATA>
) : PagingSource<Int, DATA>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DATA> {
        val position = params.key ?: 1
        return try {
            val data = getData(position)
            LoadResult.Page(
                data = data,
                prevKey = if (position == 1) null else position,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DATA>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}