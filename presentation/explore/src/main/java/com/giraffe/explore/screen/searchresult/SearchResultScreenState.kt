package com.giraffe.explore.screen.searchresult

import androidx.paging.PagingData
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.screen.discover.GenreUi
import com.giraffe.explore.screen.discover.SearchTab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SearchResultScreenState(
    val query: String = "",
    val tabs: List<SearchTab> = SearchTab.entries,
    val selectedTab: SearchTab = SearchTab.MOVIES,
    val isGridSelected: Boolean = true,
    @Transient
    val selectedPosters: Flow<PagingData<Poster>> = flowOf(),
    @Transient
    val moviesPosters: Flow<PagingData<Poster>> = flowOf(),
    @Transient
    val seriesPosters: Flow<PagingData<Poster>> = flowOf(),
    @Transient
    val actorsPosters: Flow<PagingData<Poster>> = flowOf(),
    val moviesGenres: List<GenreUi> = emptyList(),
    val seriesGenres: List<GenreUi> = emptyList(),
    val errorMessage: Int? = null,
    val isNetworkError: Boolean = false,
    val isLoading: Boolean = false,
)