package com.giraffe.media.home.usecase

import com.giraffe.media.home.repository.HomeRepository
import com.giraffe.media.movies.entity.Movie

class GetUpcomingMoviesUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(): List<Movie> = homeRepository.getUpcomingMovies()
}