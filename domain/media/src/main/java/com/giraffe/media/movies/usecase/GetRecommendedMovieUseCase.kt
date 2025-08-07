package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetRecommendedMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int, limit: Int = 10): List<Movie> {
        return repository.getRecommendedMovie(movieId, page, limit)
    }
}