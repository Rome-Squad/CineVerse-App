package com.giraffe.media.movie.usecase

import com.giraffe.media.entity.Genre
import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesGenresByIdsUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(genreIDs: List<Int>): List<Genre> =
        repository.getGenresByIds(genreIDs)
}