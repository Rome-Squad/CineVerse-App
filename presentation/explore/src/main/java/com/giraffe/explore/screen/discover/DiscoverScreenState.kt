package com.giraffe.explore.screen.discover

import androidx.annotation.StringRes
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.explore.util.HasErrorMessage

data class DiscoverScreenState(
    val errorMessageRes: Int? = null,
    val selectedGenre: GenreUi? = null,
    val selectedMovieGenre: GenreUi? = null,
    val selectedSeriesGenre: GenreUi? = null,
    val selectedGenres: List<GenreUi> = emptyList(),
    val moviesGenres: List<GenreUi> = listOf(),
    val seriesGenres: List<GenreUi> = listOf(),
    val selectedPosters: List<Poster> = emptyList(),
    val moviesPosters: List<Poster> = emptyList(),
    val seriesPosters: List<Poster> = emptyList(),
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