package  com.giraffe.media.movie.response

import  com.giraffe.media.movie.datasource.remote.dto.MovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class MoviesListResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)