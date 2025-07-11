package com.giraffe.explore

import com.giraffe.movies.entity.Movie
import com.giraffe.person.entity.Person
import com.giraffe.series.entity.Series
import kotlinx.datetime.LocalDate


data class SearchScreenState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null,

    val isSearchHistoryVisible: Boolean = true,
    val isSearchSuggestionsVisible: Boolean = false,
    val isSearchResultsVisible: Boolean = false,
    val selectedTab: SearchTab = SearchTab.MOVIES,

    val isVoiceRecording: Boolean = false,

    ///////////////////////////
    val movieResults: List<MediaStateUi> = emptyList(),
    val seriesResults: List<MediaStateUi> = emptyList(),
    val actorResults: List<ActorStateUi> = emptyList(),

    val searchHistory: List<String> = emptyList(),
    val searchSuggestions: List<String> = emptyList(),
    val recentViews: List<MediaStateUi> = emptyList()
)

enum class SearchTab {
    MOVIES, SERIES, ACTORS
}

fun Movie.toMediaStateUi(): MediaStateUi {
    return MediaStateUi(
        id = id,
        title = title,
        imageUrl = posterUrl,
        rate = rate.toString(),
        genresId = genresID,
        releaseDate = releaseYear,
        duration = duration
    )
}

fun Series.toMediaStateUi(): MediaStateUi {
    return MediaStateUi(
        id = id,
        title = title,
        imageUrl = posterUrl,
        rate = rate.toString(),
        genresId = genresID,
        releaseDate = releaseYear,
        duration = duration
    )
}

fun Person.toActorStateUi(): ActorStateUi {
    return ActorStateUi(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}


data class MediaStateUi(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rate: String,
    val genresId: List<Int>,
    val releaseDate: LocalDate,
    val duration: String
)

data class ActorStateUi(
    val id: Int,
    val name: String,
    val imageUrl: String,
)