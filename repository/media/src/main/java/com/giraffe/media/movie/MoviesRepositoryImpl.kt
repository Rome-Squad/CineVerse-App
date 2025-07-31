package com.giraffe.media.movie

import com.giraffe.media.dto.ReviewDto
import com.giraffe.media.entity.Genre
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.mapper.toEntity
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.mapper.toCacheDto
import com.giraffe.media.movie.mapper.toDto
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.media.utils.SafeCall.mapToDomainException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val local: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource,
    private val localExploreDataSource: LocalExploreDataSource
) : MoviesRepository {

    override suspend fun searchMovieByName(movieName: String, page: Int) = SafeCall {
        withContext(Dispatchers.IO) {
            val keywordData = localExploreDataSource.getSearchKeyword(query = movieName)
            val isPageCached = keywordData?.moviesPages?.contains(page) == true
            if (isPageCached) {
                local.getMoviesByName(movieName, page).map(MovieCacheDto::toEntity)
            } else {
                val remoteMovies = remote.getMoviesByName(movieName, page).map(MovieDto::toEntity)
                val updatedKeyword = keywordData?.copy(
                    moviesPages = keywordData.moviesPages + page,
                    searchedAt = System.currentTimeMillis()
                ) ?: SearchKeywordCacheDto(
                    keyword = movieName,
                    moviesPages = listOf(page)
                )
                localExploreDataSource.insertSearchKeyword(updatedKeyword)
                local.insertMovies(remoteMovies.map { it.toCacheDto().copy(page = page) })
                remoteMovies
            }
        }
    }


    override suspend fun getMovieGenres(genreIds: List<Int>) = SafeCall {
        if (genreIds.isNotEmpty()) {
            local.incrementInteractionCountForGenres(genreIds)
        }
        local.getMovieGenres(genreIds).filter { it.id in genreIds }.map { it.toEntity() }.ifEmpty {
            remote.getMovieGenres().filter { it.id in genreIds }.map(MovieGenreDto::toEntity)
        }
    }


    override suspend fun getMoviesGenres() = SafeCall {
        local.getMoviesGenres()
            .map(MovieGenreCacheDto::toEntity)
            .ifEmpty {
                remote.getMovieGenres()
                    .map(MovieGenreDto::toEntity)
                    .also { insertGenres(it) }
            }
    }


    override suspend fun getMoviesByGenre(genreId: Int, page: Int) = SafeCall {
        remote.getMoviesByGenre(genreId, page).map(MovieDto::toEntity)
    }

    override suspend fun insertMovies(movie: List<Movie>) = SafeCall {
        local.insertMovies(movie.map(Movie::toCacheDto))
    }

    override suspend fun insertGenres(genres: List<Genre>) = SafeCall {
        local.insertMovieGenres(genres.map(Genre::toDto))
    }

    override suspend fun setMovieRecent(movie: Movie, isRecent: Boolean) = SafeCall {
        local.updateMovie(movie.toCacheDto().copy(isRecent = isRecent))
    }

    override suspend fun clearCache() = SafeCall {
        local.clearMovieCache()
    }

    override fun getRecentlyMovies() =
        local.getRecentlyMovies().map { movies ->
            movies.map(MovieCacheDto::toEntity)
        }.catch { throw mapToDomainException(it) }

    override suspend fun clearRecentlyMovies() = SafeCall { local.clearRecentlyMovies() }

    override suspend fun getMovieDetails(movieId: Int) = SafeCall {
        remote.getMovieById(movieId).toEntity()
    }

    override suspend fun getRecommendedMovie(
        movieId: Int,
        page: Int
    ): List<Movie> = SafeCall {
        remote.getMovieRecommendations(movieId, page).map(MovieDto::toEntity)
    }


    override suspend fun getMovieReviews(
        movieId: Int
    ) = SafeCall { remote.getMovieReviews(movieId).map(ReviewDto::toEntity) }

    override suspend fun addRating(
        movieId: Int,
        ratingValue: Float
    ) = SafeCall {
        val requestBody = RatingRequest(value = ratingValue)
        remote.addRating(movieId, requestBody)
    }

    override suspend fun getUserMovieRating(movieId: Int) = SafeCall {
        remote.getUserMovieRating(movieId)
    }

    override suspend fun getPopularityMovies(page: Int): List<Movie> = SafeCall {
        remote.getPopularityMovies(page).map(MovieDto::toEntity)
    }

    override suspend fun getRecentlyReleasedMovies(page: Int): List<Movie> = SafeCall {
        remote.getRecentlyReleasedMovies(page).map(MovieDto::toEntity)
    }

    override suspend fun getUpcomingMovies(page: Int): List<Movie> = SafeCall {
        remote.getUpcomingMovies(page).map(MovieDto::toEntity)
    }

}