package com.giraffe.media.movie.usecase.upcoming

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class ObserveUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(limit: Int = 20) =
        movieRepository.observeUpcoming(limit = limit)

}