package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository

class GetRecommendedMovieUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): List<Movie> {
        return repository.getRecommendedMovie(movieId, page)
    }
}