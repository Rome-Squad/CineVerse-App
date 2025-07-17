package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.repository.MoviesRepository

class GetMovieGenresUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genreIDs: List<Int>): List<Genre> =
        repository.getMovieGenres(genreIDs)
}