package com.giraffe.media.movie.retrofit

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.util.RetrofitRequestBuilder
import javax.inject.Inject

class MoviesRemoteDataSourceImplRetrofit @Inject constructor(
    private val retrofitRequestBuilder: RetrofitRequestBuilder<MoviesApiServiceRetrofit>
) : MoviesRemoteDataSource {

    override suspend fun getMovieById(movieId: Int) =
        retrofitRequestBuilder.get { getMovieById(movieId) }

    override suspend fun getMoviesByName(movieName: String, page: Int) =
        retrofitRequestBuilder.get { getMoviesByName(movieName, page) }.results

    override suspend fun getMovieGenres() =
        retrofitRequestBuilder.get { getGenres() }.genres

    override suspend fun getMoviesByGenre(genreId: Int, page: Int) =
        retrofitRequestBuilder.get {
            getMoviesByGenre(
                if (genreId == -1) "" else genreId.toString(), page
            )
        }.results


    override suspend fun getMovieReviews(movieId: Int): List<ReviewDto> =
        retrofitRequestBuilder.get { getMovieReviews(movieId) }.results

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieDto> =
        retrofitRequestBuilder.get { getRecommendations(movieId, page) }.results

    override suspend fun addRating(
        movieId: Int,
        request: RatingRequest
    ) =
        retrofitRequestBuilder.post { rateMovie(movieId, request) }

    override suspend fun getUserMovieRating(movieId: Int) =
        retrofitRequestBuilder.get { getMovieRating(movieId) }
            .results.firstOrNull { it.id == movieId }?.rating?.toFloat() ?: 0f

    override suspend fun getPopularityMovies(page: Int): List<MovieDto> =
        retrofitRequestBuilder.get { getPopularMovies(page) }.results

    override suspend fun getRecentlyReleasedMovies(page: Int): List<MovieDto> =
        retrofitRequestBuilder.get { getRecentlyReleasedMovies(page) }.results

    override suspend fun getUpcomingMovies(page: Int): List<MovieDto> =
        retrofitRequestBuilder.get { getUpcomingMovies(page) }.results
}