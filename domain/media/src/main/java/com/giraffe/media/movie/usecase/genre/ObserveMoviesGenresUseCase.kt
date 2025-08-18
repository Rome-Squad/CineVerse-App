package com.giraffe.media.movie.usecase.genre

import com.giraffe.media.movie.repository.MovieRepository
import javax.inject.Inject

class ObserveMoviesGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() =
        movieRepository.observeGenres()
}