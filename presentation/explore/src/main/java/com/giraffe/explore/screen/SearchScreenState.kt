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
    val selectedTab: SearchTab = SearchTab.SERIES,
    val isVoiceRecording: Boolean = false,
    val isPermissionGranted: Boolean = false,
    val mediaResults: List<Poster> = emptyList(),
    val resultSearchKeyword: List<SearchKeyword> = emptyList(),
    val recentViews: List<Poster> = emptyList(),
    val isGridSelected: Boolean = true
) : HasErrorMessage<SearchScreenState> {
    override fun withErrorMessage(@StringRes resId: Int): SearchScreenState {
        return copy(errorMessage = resId)
    }
}

enum class SearchTab {
    MOVIES, SERIES, ACTORS
}


    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    fun Movie.toPosterMovie(allGenres: List<MovieGenre>): Poster {
        val genreTitles = allGenres
            .filter { it.id in genresID }
            .joinToString(", ") { it.title }
            .ifBlank { null }

        val fullImageUrl = posterUrl?.let { IMAGE_BASE_URL + it }.orEmpty()

        return Poster(
            id = id,
            name = title,
            imageUri = fullImageUrl,
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

        val fullImageUrl = posterUrl?.let { IMAGE_BASE_URL + it }.orEmpty()

        return Poster(
            id = id,
            name = name,
            imageUri = fullImageUrl,
            rating = rating,
            genres = genreTitles
        )
    }

    fun Person.toPosterMovie(): Poster {
        val fullImageUrl = imageUrl?.let { IMAGE_BASE_URL + it }.orEmpty()

        return Poster(
            id = id,
            name = name,
            imageUri = fullImageUrl,
            rating = 0f
        )
    }
