package com.giraffe.explore.screen.searchresult

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.screen.discover.GenreUi
import com.giraffe.explore.screen.discover.SearchTab

data class SearchResultScreenState(
    val query: String = "",
    val tabs: List<SearchTab> = SearchTab.entries,
    val selectedTab: SearchTab = SearchTab.MOVIES,
    val isGridSelected: Boolean = true,
    val selectedPosters: List<Poster> = emptyList(),
    val moviesPosters: List<Poster> = emptyList(),
    val seriesPosters: List<Poster> = emptyList(),
    val actors: List<ActorUi> = emptyList(),
    val moviesGenres: List<GenreUi> = emptyList(),
    val seriesGenres: List<GenreUi> = emptyList(),
)

data class ActorUi(
    val id: Int,
    val name: String,
    val imageUrl: String
)
