package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMoviesCollectionUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        genreId: Int? = null,
        keywords: String? = null,
        minVote: Float? = null,
        sortBy: String = "popularity.desc",
        page: Int
    ) =
        movieRepository.getMoviesCollection(
            genreId = genreId,
            keywords = keywords,
            minVote = minVote,
            sortBy = sortBy,
            page = page
        )
}