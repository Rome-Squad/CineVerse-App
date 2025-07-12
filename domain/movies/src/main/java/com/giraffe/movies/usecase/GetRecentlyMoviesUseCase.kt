package com.giraffe.movies.usecase

import com.giraffe.movies.entity.Movie
import com.giraffe.movies.repository.MoviesRepository

class GetRecentlyMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke() : List<Movie>{
       return  repository.getRecentlyMovies()
    }
}