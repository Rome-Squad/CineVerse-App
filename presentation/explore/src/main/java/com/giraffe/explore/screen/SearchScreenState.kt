package com.giraffe.explore.screen

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.movies.entity.Movie
import com.giraffe.movies.entity.MovieGenre
import com.giraffe.person.entity.Person
import com.giraffe.series.entity.Series
import com.giraffe.series.entity.SeriesGenre


data class SearchScreenState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val searchKeyword: SearchKeyword? = null,
    var errorMessage: String? = null,
    val isSearchHistoryVisible: Boolean = true,
    val isSearchSuggestionsVisible: Boolean = false,
    val isSearchResultsVisible: Boolean = false,
    val selectedTab: SearchTab = SearchTab.SERIES,
    val isVoiceRecording: Boolean = false,
    val isPermissionGranted: Boolean = false,
    val mediaResults: List<Poster> = emptyList(),
    val resultSearchKeyword: List<SearchKeyword> = emptyList(),
    val recentViews: List<Poster> = emptyList(),
    val isGridSelected: Boolean = true
)

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
        name = this@toPosterMovie.name,
        imageUri = posterUrl,
        rating = rating,
        genres = genreTitles,
    )
}

fun Person.toPosterMovie(): Poster =
    Poster(
        id = id,
        name = this@toPosterMovie.name,
        imageUri = imageUrl.orEmpty(),
        rating = 0f
    )
