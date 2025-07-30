package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genreIDs: List<Int>): List<Genre> =
        repository.getMovieGenres(genreIDs)
}