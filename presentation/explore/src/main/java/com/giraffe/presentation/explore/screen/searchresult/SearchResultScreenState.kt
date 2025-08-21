package com.giraffe.presentation.explore.screen.searchresult

import androidx.paging.PagingData
import com.giraffe.presentation.explore.components.uimodel.Poster
import com.giraffe.presentation.explore.model.GenreUi
import com.giraffe.presentation.explore.screen.discover.SearchTab
import com.giraffe.user.entity.ContentPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SearchResultScreenState(
    val query: String = "",
    val tabs: List<SearchTab> = SearchTab.entries,
    val selectedTab: SearchTab = SearchTab.MOVIES,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
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
    val isNoInternet: Boolean = false,
    val isLoading: Boolean = true,
)