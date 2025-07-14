package com.giraffe.explore.screen

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.person.entity.Person
import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre

import androidx.annotation.StringRes

data class SearchScreenState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val searchKeyword: SearchKeyword? = null,
    @StringRes val errorMessage: Int? = null,
    val isSearchHistoryVisible: Boolean = true,
    val isSearchSuggestionsVisible: Boolean = false,
    val isSearchResultsVisible: Boolean = false,

    // Tab , View Mode
    val selectedTab: SearchTab = SearchTab.MOVIES,
    val isGridSelected: Boolean = true,

    // Media Content
    //for movie and series
    val movieResults: List<Poster> = emptyList(),
    val seriesResults: List<Poster> = emptyList(),
    val actorResults: List<Poster> = emptyList(),
    val resultSearchKeyword: List<SearchKeyword> = emptyList(),
    val recentViews: List<Poster> = emptyList(),
    val searchTabs: List<SearchTab> = listOf(SearchTab.MOVIES, SearchTab.SERIES),

    // Voice Input
    val isVoiceRecording: Boolean = false,
    val isPermissionGranted: Boolean = false,
    val mediaResults: List<Poster> = emptyList(),
) : HasErrorMessage<SearchScreenState> {
    override fun withErrorMessage(@StringRes id: Int): SearchScreenState {
        return copy(errorMessage = id)
    }
}

enum class SearchTab {
    MOVIES, SERIES, ACTORS
}

fun Movie.toPosterMovie(allGenres: List<MovieGenre>): Poster {
    val genreTitles = allGenres
        .filter { it.id in genresID }
        .joinToString(", ") { it.title }
        .ifBlank { null }

    return Poster(
        id = id,
        name = title,
        imageUri = posterUrl.orEmpty(),
        rating = rating,
        genres = genreTitles,
        time = duration.toString(),
        date = releaseYear?.toString()
    )
}

fun Series.toPosterMovie(allGenres: List<SeriesGenre>): Poster {
    val genreTitles = allGenres
        .filter { it.id in genreIDs }
        .joinToString(", ") { it.name }
        .ifBlank { null }

    return Poster(
        id = id,
        name = name,
        imageUri = posterUrl,
        rating = rating,
        genres = genreTitles
    )
}

fun Person.toPoster(): Poster = Poster(
    id = id,
    name = name,
    imageUri = imageUrl.orEmpty(),
    rating = 0f
)
