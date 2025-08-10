package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class GetRecommendedMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int, limit: Int = 10): List<Movie> {
        return repository.getRecommended(movieId, page, limit)
    }
}