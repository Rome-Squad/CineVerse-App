package com.giraffe.match.screen.result

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.giraffe.match.utils.formatAsFullDate
import com.giraffe.match.utils.toDurationString
import com.giraffe.match.utils.toSeasonsString
import com.giraffe.media.collections.entity.Collection
import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.series.entity.Series
import com.giraffe.presentation.match.R
import com.giraffe.user.entity.ContentPreference

data class MatchResultScreenState(
    val matchItems: List<MatchResultModel> = emptyList(),
    val currentCarouselPage: Int = 0,
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isEmptyResults: Boolean = false,
    val isVisibleLoginBottomSheet: Boolean = false,
    val collectionBottomSheet: CollectionBottomSheet? = null,
    val contentPreference: ContentPreference = ContentPreference.HIDE_EXPLICIT,
    val collections: List<CollectionUi> = emptyList(),
    val newCollectionName: String = "",
    val genreIds: List<Int> = emptyList(),
    val moodId: String = "",
    val runtime: Int? = null,
    val releaseDate: String? = null,
    val minRuntime: Int? = null,
    val maxRuntime: Int? = null,
    val earliestDate: String? = null,
    val latestDate: String? = null,
    val currentSelectedItemId: Int? = null,
    val currentSelectedMediaType: MediaType? = null
) {
    @Stable
    sealed class CollectionBottomSheet {
        object AddToCollection : CollectionBottomSheet()
        object CreateCollection : CollectionBottomSheet()
        object NoCollections : CollectionBottomSheet()
    }
}

data class CollectionUi(
    val id: Int,
    val title: String,
    val isLoading: Boolean
)
data class MatchResultModel(
    val id: Int,
    val title: String = "",
    val posterUrl: String = "",
    val genres: List<String> = emptyList(),
    val mediaType: MediaType = MediaType.MOVIE,
    val duration: String = "",
    val releaseDate: String = "",
    val rating: Float = 0f,
    val youtubeVideoId: String = ""
)

enum class MediaType(@param:StringRes val labelRes: Int) {
    MOVIE(R.string.media_type_movie),
    SERIES(R.string.media_type_series)
}

fun Collection.toUi() = CollectionUi(
    id = this.id,
    title = this.name,
    isLoading = false
)

fun Movie.toMatchResultModel(genres: List<String>): MatchResultModel {
    return MatchResultModel(
        id = id,
        title = name,
        posterUrl = posterUrl,
        genres = genres,
        mediaType = MediaType.MOVIE,
        duration = duration.toDurationString(),
        releaseDate = releaseYear.formatAsFullDate(),
        rating = rating,
        youtubeVideoId = youtubeVideoId
    )
}

fun Series.toMatchResultModel(
    genres: List<String>,
): MatchResultModel {
    return MatchResultModel(
        id = id,
        title = name,
        posterUrl = posterUrl,
        genres = genres,
        mediaType = MediaType.SERIES,
        duration = seasons.size.toSeasonsString(),
        releaseDate = releaseYear.formatAsFullDate(),
        rating = rating,
        youtubeVideoId = youtubeVideoId ?: ""
    )
}
