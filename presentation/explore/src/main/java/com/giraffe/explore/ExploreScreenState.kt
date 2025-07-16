package com.giraffe.explore

import androidx.annotation.StringRes
import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.explore.entity.SearchKeyword
import com.giraffe.media.explore.util.HasErrorMessage
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.person.entity.Person
import com.giraffe.media.series.entity.Series
import com.giraffe.media.series.entity.SeriesGenre

data class ExploreScreenState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val searchKeyword: SearchKeyword? = null,
    val errorMessage: Int? = null,
    val isSearchHistoryVisible: Boolean = true,
    val isSearchSuggestionsVisible: Boolean = false,
    val isSearchResultsVisible: Boolean = false,
    val selectedGenre: GenreUi? = null,
    val selectedGenres: List<GenreUi> = emptyList(),
    val moviesGenres: List<GenreUi> = listOf(),
    val seriesGenres: List<GenreUi> = listOf(),
    val isSearchFieldFocused: Boolean = false,
    val selectedPosters: List<Poster> = emptyList(),
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
    val selectedMovieGenre: GenreUi? = null,
    val selectedSeriesGenre: GenreUi? = null,
) : HasErrorMessage<ExploreScreenState> {
    override fun withErrorMessage(@StringRes id: Int): ExploreScreenState {
        return copy(errorMessage = id, isLoading = false)
    }
}

const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

enum class SearchTab {
    MOVIES, SERIES, ACTORS
}

fun Movie.toPoster(allGenres: List<GenreUi>): Poster {
    val genreTitles = allGenres
        .filter { it.id in genresID }
        .joinToString(", ") { it.title }
        .ifBlank { null }

    return Poster(
        id = id,
        name = title,
        imageUri = posterUrl?.let { TMDB_IMAGE_BASE_URL + it }.orEmpty(),
        rating = rating,
        genres = genreTitles,
        time = duration.toString(),
        date = releaseYear?.toString()
    )
}

fun Series.toPoster(allGenres: List<GenreUi>): Poster {
    val genreTitles = allGenres
        .filter { it.id in genreIDs }
        .joinToString(", ") { it.title }
        .ifBlank { null }

    return Poster(
        id = id,
        name = name,
        imageUri = posterUrl.let { TMDB_IMAGE_BASE_URL + it },
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

data class GenreUi(
    val id: Int = -1,
    val title: String
)

fun MovieGenre.toUi() = GenreUi(id, title)
fun SeriesGenre.toUi() = GenreUi(id, name)
