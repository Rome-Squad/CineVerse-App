package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.user.usecase.GetUserUseCase
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke() =
        movieRepository.getUserRated(getUserUseCase().id)
}