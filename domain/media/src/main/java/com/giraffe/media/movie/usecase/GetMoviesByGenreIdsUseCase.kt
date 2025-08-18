package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreIdsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genreIds: List<Int>, page: Int) =
        movieRepository.getByGenreIds(genreIds = genreIds, page = page)
}