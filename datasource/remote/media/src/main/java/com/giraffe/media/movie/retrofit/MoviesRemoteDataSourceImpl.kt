package com.giraffe.media.movie.retrofit

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.util.safeCallRemote
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val moviesApiService: MoviesApiService,
) : MoviesRemoteDataSource {

    override suspend fun getMovieById(movieId: Int) =
        safeCallRemote { moviesApiService.getMovieById(movieId) }

    override suspend fun getMoviesByName(movieName: String, page: Int) =
        safeCallRemote { moviesApiService.getMoviesByName(movieName, page) }.results

    override suspend fun getMovieGenres() =
        safeCallRemote { moviesApiService.getGenres() }.genres

    override suspend fun getMoviesByGenre(genreId: Int, page: Int) =
        safeCallRemote {
            moviesApiService.discoverMovies(
                genreId = if (genreId == -1) "" else genreId.toString(),
                page = page
            )
        }.results

    override suspend fun getMoviesByGenreIds(
        genreIds: List<Int>,
        page: Int
    ) =
        safeCallRemote {
            moviesApiService.discoverMovies(
                genreId = genreIds.joinToString(","),
                page = page
            )
        }.results

    override suspend fun getMoviesByKeywordsId(
        keywords: Int,
        page: Int
    ) =
        safeCallRemote {
            moviesApiService.discoverMovies(
                keywords = keywords.toString(),
                page = page
            )
        }.results

    override suspend fun getMoviesBySort(
        sortBy: String,
        page: Int
    ) =
        safeCallRemote {
            moviesApiService.discoverMovies(
                sortBy = sortBy,
                page = page
            )
        }.results


    override suspend fun getMovieReviews(movieId: Int, page: Int): List<ReviewDto> =
        safeCallRemote { moviesApiService.getMovieReviews(movieId, page = page) }.results

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MovieDto> =
        safeCallRemote { moviesApiService.getRecommendations(movieId, page) }.results

    override suspend fun addRating(movieId: Int, request: RatingRequest) =
        safeCallRemote { moviesApiService.rateMovie(movieId, request) }

    override suspend fun getUserMovieRating(movieId: Int) =
        safeCallRemote { moviesApiService.getUserMovieRating(movieId) }.getRating()

    override suspend fun getPopularityMovies(page: Int): List<MovieDto> =
        safeCallRemote { moviesApiService.getPopularMovies(page) }.results

    override suspend fun getRecentlyReleasedMovies(page: Int): List<MovieDto> =
        safeCallRemote { moviesApiService.getRecentlyReleasedMovies(page) }.results

    override suspend fun getUpcomingMovies(page: Int): List<MovieDto> =
        safeCallRemote { moviesApiService.getUpcomingMovies(page) }.results

    override suspend fun getMovieTrailerUrl(movieId: Int): String {
        val results = safeCallRemote { moviesApiService.getMovieTrailerUrl(movieId) }.results
        return results.firstOrNull { it.type == "Trailer" }?.key
            ?: results.firstOrNull()?.key.orEmpty()
    }

    override suspend fun getRatedMovies(accountId: Int) =
        safeCallRemote { moviesApiService.getRatedMovies(accountId) }.results

    override suspend fun deleteMovieRating(movieId: Int) =
        safeCallRemote { moviesApiService.deleteMovieRating(movieId) }

}