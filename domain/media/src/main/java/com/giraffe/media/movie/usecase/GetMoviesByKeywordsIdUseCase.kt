package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByKeywordsIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(keywords: Int, page: Int) =
        movieRepository.getByKeywordsId(keywords = keywords, page = page)
}