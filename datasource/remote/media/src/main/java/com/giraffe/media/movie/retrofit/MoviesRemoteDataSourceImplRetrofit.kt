package com.giraffe.media.movie.retrofit

import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieReviewDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.util.RetrofitRequestBuilder

class MoviesRemoteDataSourceImplRetrofit(
    private val RetrofitRequestBuilder: RetrofitRequestBuilder<MoviesApiServiceRetrofit>
) : MoviesRemoteDataSource {

    override suspend fun getMovieById(movieId: Int) =
        RetrofitRequestBuilder.get { getMovieById(movieId) }

    override suspend fun getMoviesByName(movieName: String) =
        RetrofitRequestBuilder.get { getMoviesByName(movieName) }.results

    override suspend fun getMovieGenres() =
        RetrofitRequestBuilder.get { getGenres() }.genres

    override suspend fun getMoviesByGenre(genreId: Int) =
        RetrofitRequestBuilder.get {
            getMoviesByGenre(
                if (genreId == -1) "" else genreId.toString()
            )
        }.results


    override suspend fun getMovieReviews(movieId: Int): List<MovieReviewDto> =
        RetrofitRequestBuilder.get { getMovieReviews(movieId) }.results

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieDto> =
        RetrofitRequestBuilder.get { getRecommendations(movieId, page) }.results

    override suspend fun addRating(movieId: Int, sessionId: String, request: RatingRequest) =
        RetrofitRequestBuilder.post { rateMovie(movieId, sessionId, request) }

    override suspend fun getUserMovieRating(movieId: Int, sessionId: String) = RetrofitRequestBuilder.get { getMovieRating(movieId, sessionId) }
        .results.firstOrNull { it.id == movieId }?.rating?.toFloat() ?: 0f

    override suspend fun deleteRating(movieId: Int, sessionId: String) =
        RetrofitRequestBuilder.delete { deleteMovieRating(movieId, sessionId) }

}