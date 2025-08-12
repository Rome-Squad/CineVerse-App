package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByNameUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieName: String, page: Int): List<Movie> {
        val results = movieRepository.getByName(name = movieName, page = page)
        return movieRepository.getTopGenre()?.let { genre ->
            results.sortedByDescending { genre.id in it.genresID }
        } ?: results
    }
}