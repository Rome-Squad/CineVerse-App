package com.giraffe.details.screens.moviedetails.model

import com.giraffe.media.movies.entity.Movie
import kotlinx.datetime.LocalDate

data class MovieUi(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val rating: Float = 0.0f,
    val duration: Int? = null,
    val genresID: List<Int> = emptyList(),
    val posterUrl: String? = null,
    val releaseYear: String = ""
) {
    fun toEntity() = Movie(
        id = id,
        title = title,
        description = description,
        rating = rating,
        duration = duration,
        posterUrl = posterUrl,
        genresID = genresID,
        releaseYear = LocalDate.parse(releaseYear)
    )

    companion object {
        fun fromEntity(entity: Movie) = MovieUi(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            rating = entity.rating,
            duration = entity.duration,
            posterUrl = entity.posterUrl,
            genresID = entity.genresID,
            releaseYear = entity.releaseYear.toString()
        )
    }
}
