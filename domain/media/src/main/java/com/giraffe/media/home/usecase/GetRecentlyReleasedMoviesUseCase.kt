package com.giraffe.media.home.usecase

import com.giraffe.media.home.repository.HomeRepository
import com.giraffe.media.movies.entity.Movie

class GetRecentlyReleasedMoviesUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): List<Movie> = homeRepository.getRecentlyReleasedMovies()
}