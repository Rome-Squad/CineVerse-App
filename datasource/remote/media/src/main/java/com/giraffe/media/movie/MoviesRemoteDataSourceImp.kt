package  com.giraffe.media.movie

import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDetailsDto
import com.giraffe.media.movie.datasource.remote.dto.RatedMoviesResponse
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.datasource.remote.dto.ReviewsResponseDto
import com.giraffe.media.movie.exceptions.NetworkException
import com.giraffe.media.movie.response.GenreResponse
import com.giraffe.media.movie.response.MoviesListResponse
import com.giraffe.media.util.RequestBuilder

class MoviesRemoteDataSourceImp(
    private val requestBuilder: RequestBuilder
) : MoviesRemoteDataSource {
    override suspend fun getMovieById(movieId: Int) =
        requestBuilder.get<MovieDetailsDto>(endpoint = "$MOVIE_BY_ID_URL/$movieId")

    override suspend fun getMoviesByName(movieName: String) =
        requestBuilder.get<MoviesListResponse>(endpoint = MOVIES_BY_NAME_URL).results

    override suspend fun getMovieGenres() =
        requestBuilder.get<GenreResponse>(endpoint = GENRES_URL).genres


    override suspend fun getMoviesByGenres(genreIds: List<Int>) =
        requestBuilder.get<MoviesListResponse>(
            endpoint = MOVIES_BY_GENRE_URL,
            params = mapOf(WITH_GENRES to genreIds.joinToString(","))
        ).results


    override suspend fun getMovieReviews(movieId: Int) =
        requestBuilder.get<ReviewsResponseDto>(endpoint = "/movie/$movieId/reviews").results


    override suspend fun getUserMovieRating(movieId: Int, guestSessionId: String) =
        requestBuilder.get<RatedMoviesResponse>(endpoint = "guest_session/$guestSessionId/rated/movies").results.firstOrNull {
            it.id == movieId
        }?.rating?.toFloat() ?: throw NetworkException()


    override suspend fun addRating(
        movieId: Int,
        sessionId: String,
        request: RatingRequest
    ) {
        /*
        return handleRequest {
            client.post("$baseUrl/movie/$movieId/rating") {
                url {
                    parameters.append("guest_session_id", sessionId)
                }
                contentType(ContentType.Application.Json)
                headers {
                    append("Authorization", "Bearer $accessToken")
                    append("Accept", "application/json")
                }
                setBody(request)
            }
        }*/
    }

    companion object {
        private const val MOVIE_BY_ID_URL = "movie"
        private const val MOVIES_BY_NAME_URL = "search/movie"
        private const val GENRES_URL = "genre/movie/list"
        private const val MOVIES_BY_GENRE_URL = "discover/movie"
        private const val WITH_GENRES = "with_genres"
    }
}