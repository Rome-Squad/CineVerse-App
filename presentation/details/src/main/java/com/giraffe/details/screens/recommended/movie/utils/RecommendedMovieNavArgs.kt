package com.giraffe.details.screens.recommended.movie.utils

import android.net.Uri
import android.os.Bundle
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedMovieArgs(
    val movieId: Int,
    val title: String
) {
    fun toRoute(): String {
        return "recommended_movies/$movieId/${Uri.encode(title)}"
    }

    companion object {
        const val ROUTE = "recommended_movies"
        const val MOVIE_ID = "movieId"
        const val TITLE = "title"

        val fullRoute = "$ROUTE/{$MOVIE_ID}/{$TITLE}"
    }
}