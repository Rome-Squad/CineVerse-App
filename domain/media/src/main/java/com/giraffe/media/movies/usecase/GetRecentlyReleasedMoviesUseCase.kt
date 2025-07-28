package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetRecentlyReleasedMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend operator fun invoke(page: Int): List<Movie> = repository.getRecentlyReleasedMovies(page)
}