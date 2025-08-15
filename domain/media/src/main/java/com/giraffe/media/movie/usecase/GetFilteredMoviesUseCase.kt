package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetFilteredMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        genreId: List<Int>? = null,
        keywords: String? = null,
        sortBy: String = "popularity.desc",
        page: Int
    ) =
        movieRepository.discoverMovies(
            genreId = genreId,
            keywords = keywords,
            sortBy = sortBy,
            page = page
        )
}