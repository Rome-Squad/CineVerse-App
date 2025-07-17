package com.giraffe.details.base

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BasePagingSource<DATA : Any>(
    val getData: suspend (Int) -> List<DATA>
) : PagingSource<Int, DATA>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DATA> {
        val nextPageIndex = params.key ?: 1
        return LoadResult.Page(
            data = getData(nextPageIndex),
            prevKey = null,
            nextKey = nextPageIndex
        )
    }

    override fun getRefreshKey(state: PagingState<Int, DATA>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}