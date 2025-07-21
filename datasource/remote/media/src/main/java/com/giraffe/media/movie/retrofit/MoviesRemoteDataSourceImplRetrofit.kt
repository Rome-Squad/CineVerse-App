package com.giraffe.media.movie.retrofit

import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieReviewDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.util.RetrofitRequestBuilder

class MoviesRemoteDataSourceImplRetrofit(
    private val builder: RetrofitRequestBuilder<MoviesApiServiceRetrofit>
) : MoviesRemoteDataSource {

    override suspend fun getMovieById(movieId: Int) =
        builder.get { getMovieById(movieId) }

    override suspend fun getMoviesByName(movieName: String) =
        builder.get { getMoviesByName(movieName) }.results

    override suspend fun getMovieGenres() =
        builder.get { getGenres() }.genres

    override suspend fun getMoviesByGenre(genreId: Int) =
        builder.get {
            getMoviesByGenre(
                if (genreId == -1) "" else genreId.toString()
            )
        }.results


    override suspend fun getMovieReviews(movieId: Int): List<MovieReviewDto> =
        builder.get { getMovieReviews(movieId) }.results

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieDto> =
        builder.get { getRecommendations(movieId, page) }.results

    override suspend fun addRating(movieId: Int, sessionId: String, request: RatingRequest) =
        builder.post { rateMovie(movieId, sessionId, request) }

    override suspend fun getUserMovieRating(movieId: Int, guestSessionId: String) = builder.get { getMovieRating(movieId, guestSessionId) }
        .results.firstOrNull { it.id == movieId }?.rating?.toFloat() ?: 0f

}
