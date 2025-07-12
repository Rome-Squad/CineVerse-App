package com.giraffe.explore.screen

import com.giraffe.designsystem.uimodel.PosterMovie
import com.giraffe.explore.entity.SearchKeyword
import com.giraffe.movies.entity.Movie


data class SearchScreenState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null,

    val isSearchHistoryVisible: Boolean = true,
    val isSearchSuggestionsVisible: Boolean = false,
    val isSearchResultsVisible: Boolean = false,
    val selectedTab: SearchTab = SearchTab.ACTORS,

    val isVoiceRecording: Boolean = false,

    val movieResults: List<PosterMovie> = emptyList(),
    val seriesResults: List<PosterMovie> = emptyList(),
    val actorResults: List<PosterMovie> = emptyList(),

    val resultSearchKeyword: List<SearchKeywordResults> = listOf(
        SearchKeywordResults(
            keyword = "",
            isFromSearchHistory = true,
        )
    ),

    val recentViews: List<PosterMovie> = emptyList(),
    val isGridSelected: Boolean = true
)

enum class SearchTab {
    MOVIES, SERIES, ACTORS
}

//fun Movie.toMovieUi(): PosterMovie {
//    return PosterMovie(
//
//        title = title,
//        imageUri = posterUrl?:"",
//        rating = rating,
//        genres = genresID,
//        releaseDate = releaseYear,
//        duration = duration.toString()
//    )
//}
//
//fun Series.toSeriesUi(): SeriesStateUi {
//    return SeriesStateUi(
//        id = id,
//        title = title,
//        imageUrl = posterUrl,
//        rate = rate.toString(),
//        genres = genresID,
//        releaseDate = releaseYear,
//    )
//}
//
//fun Person.toActorStateUi(): ActorStateUi {
//    return ActorStateUi(
//        id = id,
//        name = name,
//        imageUrl = imageUrl.toString()
//    )
//}

fun SearchKeyword.toSearchKeywordResults(): SearchKeywordResults {
    return SearchKeywordResults(
        keyword = keyword,
        isFromSearchHistory = isFromSearchHistory
    )
}

//data class RecentViewsStateUi(
//    val title: String,
//    val imageUrl: String,
//    val rate: String? = null
//)

//data class MovieStateUi(
//    val id: Int,
//    val title: String,
//    val imageUrl: String,
//    val rate: String,
//    val genres: List<Int>,
//    val releaseDate: LocalDate,
//    val duration: String
//)
//
//data class SeriesStateUi(
//    val id: Int,
//    val title: String,
//    val imageUrl: String,
//    val rate: String,
//    val genres: List<Int>,
//    val releaseDate: LocalDate,
//)
//
//
//data class ActorStateUi(
//    val id: Int,
//    val name: String,
//    val imageUrl: String,
//)

data class SearchKeywordResults(
    val keyword: String,
    val isFromSearchHistory: Boolean,
)