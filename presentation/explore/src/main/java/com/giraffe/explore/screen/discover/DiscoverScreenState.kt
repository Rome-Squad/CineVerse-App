package com.giraffe.explore.screen.discover

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.util.HasErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class DiscoverScreenState(
    val errorMessageRes: Int? = null,
    val selectedGenre: GenreUi? = null,
    val selectedMovieGenre: GenreUi? = null,
    val selectedSeriesGenre: GenreUi? = null,
    val selectedGenres: List<GenreUi> = emptyList(),
    val moviesGenres: List<GenreUi> = listOf(),
    val seriesGenres: List<GenreUi> = listOf(),
    @Transient
    val selectedPosters: Flow<PagingData<Poster>> = flowOf(),
    @Transient
    val moviesPosters: Flow<PagingData<Poster>> = flowOf(),
    @Transient
    val seriesPosters: Flow<PagingData<Poster>> = flowOf(),
    val selectedTab: SearchTab = SearchTab.MOVIES,
    val tabs: List<SearchTab> = listOf(SearchTab.MOVIES, SearchTab.SERIES),
    val isGridSelected: Boolean = true,
) : HasErrorMessage<DiscoverScreenState> {
    override fun withErrorMessage(@StringRes id: Int): DiscoverScreenState {
        return copy(errorMessageRes = id)
    }
}

data class GenreUi(
    val id: Int = -1,
    val title: String
)

enum class SearchTab {
    MOVIES, SERIES, ACTORS
}