package  com.giraffe.media.movie

import com.giraffe.media.exception.NoInternetException
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.model.dto.MovieDetailsDto
import com.giraffe.media.movie.model.dto.RatedMoviesResponse
import com.giraffe.media.movie.model.dto.RatingRequest
import com.giraffe.media.movie.model.dto.ReviewsResponseDto
import com.giraffe.media.movie.response.GenreResponse
import com.giraffe.media.movie.response.MoviesListResponse
import com.giraffe.media.util.RequestBuilder

class MoviesRemoteDataSourceImp(
    private val requestBuilder: RequestBuilder
) : MoviesRemoteDataSource {
    override suspend fun getMovieById(movieId: Int) =
        requestBuilder.get<MovieDetailsDto>(endpoint = "$MOVIE_END_PINT/$movieId")

    override suspend fun getMoviesByName(movieName: String) =
        requestBuilder.get<MoviesListResponse>(
            endpoint = MOVIES_BY_NAME_URL,
            params = mapOf(QUERY to movieName)
        ).results

    override suspend fun getMovieGenres() =
        requestBuilder.get<GenreResponse>(endpoint = GENRES_URL).genres


    override suspend fun getMoviesByGenre(genreId: Int) =
        requestBuilder.get<MoviesListResponse>(
            endpoint = MOVIES_BY_GENRE_URL,
            params = mapOf(WITH_GENRES to (if (genreId == -1) "" else genreId.toString()))
        ).results


    override suspend fun getMovieReviews(movieId: Int) =
        requestBuilder.get<ReviewsResponseDto>(endpoint = "$MOVIE_END_PINT/$movieId/$REVIEWS_END_PINT").results


    override suspend fun getUserMovieRating(movieId: Int, guestSessionId: String) =
        requestBuilder.get<RatedMoviesResponse>(endpoint = "$GESST_SESSION_END_PINT/$guestSessionId/$RATED_END_PINT/$MOVIES_END_PINT").results.firstOrNull {
            it.id == movieId
        }?.rating?.toFloat() ?: throw NoInternetException()


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
        private const val MOVIE_END_PINT = "movie"
        private const val MOVIES_END_PINT = "movies"
        private const val RATED_END_PINT = "rated"
        private const val REVIEWS_END_PINT = "reviews"
        private const val GESST_SESSION_END_PINT = "guest_session"
        private const val MOVIES_BY_NAME_URL = "search/movie"
        private const val GENRES_URL = "genre/movie/list"
        private const val MOVIES_BY_GENRE_URL = "discover/movie"
        private const val WITH_GENRES = "with_genres"
        private const val QUERY = "query"
    }
}