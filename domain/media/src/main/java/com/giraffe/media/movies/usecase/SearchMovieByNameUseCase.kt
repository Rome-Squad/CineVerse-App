package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import javax.inject.Inject

class SearchMovieByNameUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieName: String, page: Int): List<Movie> {
        val results = repository.searchMovieByName(movieName, page)
        val topGenre = repository.getMoviesGenres().firstOrNull { it.rank != 0 }
        return topGenre?.let { genre ->
            results.sortedByDescending { it.genresID.contains(genre.id) }
        } ?: results
    }
}