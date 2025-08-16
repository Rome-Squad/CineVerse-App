package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMoviesBySortUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(sortBy: String, page: Int) =
        movieRepository.getBySort(sortBy = sortBy, page = page)
}