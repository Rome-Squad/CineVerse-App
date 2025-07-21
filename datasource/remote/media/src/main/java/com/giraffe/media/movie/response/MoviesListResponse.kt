package  com.giraffe.media.movie.response

import  com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)