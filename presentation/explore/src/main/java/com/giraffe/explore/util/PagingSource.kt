package com.giraffe.explore.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.giraffe.explore.screen.searchresult.ActorUi
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase

class SearchPeoplePagingSource(
    private val query: String,
    private val pageSize: Int,
    private val searchPeopleByName: SearchPeopleByNameUseCase
) : PagingSource<Int, ActorUi>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ActorUi> {
        return try {
            val nextPage = params.key ?: 1
            val result = searchPeopleByName(query, nextPage)
            val actors = result.data.map(Person::toUi)
            val maxPages = result.totalResults / pageSize
            LoadResult.Page(
                data = actors,
                prevKey = null,
                nextKey = if (nextPage == maxPages) null else nextPage.inc()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ActorUi>): Int? {
        return state.anchorPosition
    }
}