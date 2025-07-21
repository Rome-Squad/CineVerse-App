package com.giraffe.media.movies.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movies.repository.MoviesRepository

class GetMoviesGenresByIdsUseCase(moviesRepository: MoviesRepository) {
    suspend operator fun invoke(genreIDs: List<Int>): List<Genre> {
        return emptyList()
    }
}