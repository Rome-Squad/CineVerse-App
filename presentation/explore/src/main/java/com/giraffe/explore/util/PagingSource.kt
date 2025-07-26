package com.giraffe.explore.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.giraffe.explore.screen.searchresult.ActorUi
import com.giraffe.media.person.entity.Person
import com.giraffe.media.person.usecase.SearchPeopleByNameUseCase

class SearchPeoplePagingSource(
    private val query: String,
    private val searchPeopleByName: SearchPeopleByNameUseCase
) : PagingSource<Int, ActorUi>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ActorUi> {
        return try {
            val nextPage = params.key ?: 1
            val actors = searchPeopleByName(query, nextPage).map(Person::toUi)
            LoadResult.Page(
                data = actors,
                prevKey = null,
                nextKey = if (actors.isEmpty()) null else nextPage.inc()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ActorUi>): Int? {
        return state.anchorPosition
    }
}