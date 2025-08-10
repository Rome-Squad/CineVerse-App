package com.giraffe.match.screen.result

import androidx.annotation.StringRes
import com.giraffe.presentation.match.R

data class MatchResultScreenState(
    val matchItems: List<MatchResultModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMsgRes: Int? = null,
    val isGenericError: Boolean = false,
    val isNetworkError: Boolean = false
)

data class MatchResultModel(
    val id: Int,
    val title: String = "",
    val posterUrl: String = "",
    val genres: List<String> = emptyList(),
    val rating: Float = 0f,
    val mediaType: MediaType = MediaType.MOVIE,
    val duration: String = "",
    val releaseDate: String = "",
    val youtubeVideoId: String = ""
)

enum class MediaType(@param:StringRes val labelRes: Int) {
    MOVIE(R.string.media_type_movie),
    SERIES(R.string.media_type_series)
}
