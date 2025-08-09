package com.giraffe.presentation.profile.model

import com.giraffe.designsystem.uimodel.Poster
import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.series.entity.Series

data class RatedPoster(
    val poster: Poster,
    val rating: Float
) {
    companion object {
        fun fromMovie(
            movie: Movie,
            rating: Float,
            genres: List<Genre>
        ) = RatedPoster(
            poster = movie.toPoster(genres),
            rating = rating
        )

        fun fromSeries(
            series: Series,
            rating: Float,
            genres: List<Genre>
        ) = RatedPoster(
            poster = series.toPoster(genres),
            rating = rating
        )
    }
}

