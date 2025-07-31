package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesByGenresUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genreId: Int, page: Int): List<Movie> {
        return repository.getMoviesByGenre(genreId, page)
    }
}