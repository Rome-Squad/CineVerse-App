package com.giraffe.media.movie

import com.giraffe.media.entity.Genre
import com.giraffe.media.exception.NoInternetDataException
import com.giraffe.media.exception.UserNotLoggedInException
import com.giraffe.media.explore.datasource.local.LocalExploreDataSource
import com.giraffe.media.explore.datasource.local.cacheDto.SearchKeywordCacheDto
import com.giraffe.media.movie.datasource.local.MoviesLocalDataSource
import com.giraffe.media.movie.datasource.local.cacheDto.MovieCacheDto
import com.giraffe.media.movie.datasource.local.cacheDto.MovieGenreCacheDto
import com.giraffe.media.movie.datasource.remote.MoviesRemoteDataSource
import com.giraffe.media.movie.datasource.remote.dto.MovieDto
import com.giraffe.media.movie.datasource.remote.dto.MovieGenreDto
import com.giraffe.media.movie.datasource.remote.dto.MovieReviewDto
import com.giraffe.media.movie.datasource.remote.dto.RatingRequest
import com.giraffe.media.movie.mapper.toCacheDto
import com.giraffe.media.movie.mapper.toDto
import com.giraffe.media.movie.mapper.toEntity
import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.media.utils.SafeCall
import com.giraffe.user.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val local: MoviesLocalDataSource,
    private val remote: MoviesRemoteDataSource,
    private val sessionManager: SessionManager,
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

    override suspend fun getRecentlyMovies() =
        SafeCall { local.getRecentlyMovies().map(MovieCacheDto::toEntity) }

    override suspend fun clearRecentlyMovies() = SafeCall { local.clearRecentlyMovies() }

    override suspend fun getMovieDetails(movieId: Int) = SafeCall {
//        Log.d("TAG", "getMovieDetails: ${cache.getMovieById(movieId)?.toEntity()}")
//        Log.d("TAG", "getMovieDetails: ${remote.getMovieById(movieId)}")
        local.getMovieById(movieId)?.toEntity() ?: remote.getMovieById(movieId)
            .toEntity()
            .also { insertMovies(listOf(it)) }
    }

    override suspend fun getRecommendedMovie(
        movieId: Int,
        page: Int
    ): List<Movie> = SafeCall {
        remote.getMovieRecommendations(movieId, page).map(MovieDto::toEntity)
    }


    override suspend fun getMovieReviews(
        movieId: Int
    ) = SafeCall { remote.getMovieReviews(movieId).map(MovieReviewDto::toEntity) }

    override suspend fun addRating(
        movieId: Int,
        ratingValue: Float
    ) = SafeCall {
        val sessionId = getSessionId()
        if (sessionId.isBlank()) {
            throw UserNotLoggedInException()
        }
        val requestBody = RatingRequest(value = ratingValue)
        remote.addRating(movieId, sessionId, requestBody)
    }

    override suspend fun getUserMovieRating(movieId: Int) = SafeCall {
        val sessionId = getSessionId()
        remote.getUserMovieRating(movieId, sessionId)
    }

    override suspend fun deleteMovieRating(movieId: Int) = SafeCall {
        val sessionId = getSessionId()

        if (sessionId.isBlank()) {
            throw UserNotLoggedInException()
        }
        remote.deleteRating(movieId, sessionId)
    }

    override suspend fun getPopularityMovies(page: Int): List<Movie> = SafeCall{
        remote.getPopularityMovies(page).map ( MovieDto::toEntity)
    override suspend fun getPopularityMovies(page: Int): List<Movie> = SafeCall {
        remote.getPopularityMovies(page).map(MovieDto::toEntity)
    }

    override suspend fun getRecentlyReleasedMovies(page: Int): List<Movie> = SafeCall {
        remote.getRecentlyReleasedMovies(page).map(MovieDto::toEntity)
    }

    override suspend fun getUpcomingMovies(page: Int): List<Movie> = SafeCall {
        remote.getUpcomingMovies(page).map(MovieDto::toEntity)
    }

    private suspend fun getSessionId() = SafeCall {
        sessionManager.getSessionId() ?: throw NoInternetDataException()
    }
}