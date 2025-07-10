package com.giraffe.movies.usecase

import com.giraffe.movies.entity.Movie
import com.giraffe.movies.repository.MoviesRepository

class GetMoviesByGenreUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genreId: Int): List<Movie> {
        return repository.getMoviesByGenre(genreId)
    }
}