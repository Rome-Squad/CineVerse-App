package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMoviesGenresByIdsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genreIDs: List<Int>) =
        movieRepository.getGenresByIds(genreIDs)
}