package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetRecentlyMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getRecentlyMovies()
    }
}