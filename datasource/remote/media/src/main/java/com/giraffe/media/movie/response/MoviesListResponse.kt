package  com.giraffe.media.movie.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListResponse<T>(
    val id: Int? = null,
    val page: Int? = null,
    val results: List<T>,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
)