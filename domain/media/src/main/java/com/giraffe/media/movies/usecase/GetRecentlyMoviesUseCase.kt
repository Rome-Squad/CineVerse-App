package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetRecentlyMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getRecentlyMovies()
    }
}