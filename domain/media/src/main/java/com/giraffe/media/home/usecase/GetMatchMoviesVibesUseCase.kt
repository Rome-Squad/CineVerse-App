package com.giraffe.media.home.usecase

import com.giraffe.media.home.repository.HomeRepository
import com.giraffe.media.movies.entity.Movie

class GetMatchMoviesVibesUseCase(homeRepository: HomeRepository) {
    operator fun invoke(movieId: String): List<Movie> {
        return emptyList()
    }
}