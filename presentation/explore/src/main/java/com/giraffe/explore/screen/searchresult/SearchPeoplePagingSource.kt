package com.giraffe.explore.screen.searchresult

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase
import com.giraffe.explore.util.toUi

class SearchPeoplePagingSource(
    private val query: String,
    private val searchPeopleByName: SearchPeopleByNameUseCase
) : PagingSource<Int, ActorUi>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ActorUi> {
        val page = params.key ?: 1
        return try {
            val actors = searchPeopleByName(query, page).map { it.toUi() }
            LoadResult.Page(
                data = actors,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (actors.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ActorUi>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
