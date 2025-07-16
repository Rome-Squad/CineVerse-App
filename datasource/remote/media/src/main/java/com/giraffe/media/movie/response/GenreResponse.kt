package  com.giraffe.media.movie.response

import  com.giraffe.media.movie.model.dto.MovieGenreDto
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres: List<MovieGenreDto>
)