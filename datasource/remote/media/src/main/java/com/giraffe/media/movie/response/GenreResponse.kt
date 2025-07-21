package  com.giraffe.media.movie.response

import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto


data class GenreResponse(
    val genres: List<MovieGenreDto>
)