package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.MovieGenre
import com.giraffe.media.movies.repository.MoviesRepository

class GetMovieGenresUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genreIDs: List<Int>): List<MovieGenre> =
        repository.getMovieGenres(genreIDs)
}