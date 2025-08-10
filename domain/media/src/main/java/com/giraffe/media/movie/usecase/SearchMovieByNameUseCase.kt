package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.repository.MoviesRepository
import javax.inject.Inject

class SearchMovieByNameUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieName: String, page: Int): List<Movie> {
        val results = repository.getByName(movieName, page)
        val topGenre = repository.getGenres().firstOrNull { it.rank != 0 }
        return topGenre?.let { genre ->
            results.sortedByDescending { it.genresID.contains(genre.id) }
        } ?: results
    }
}