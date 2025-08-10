package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesByGenresUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genreId: Int, page: Int): List<Movie> {
        return repository.getByGenreId(genreId, page)
    }
}