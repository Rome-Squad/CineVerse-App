package com.giraffe.media.movie.usecase.rate

import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.user.usecase.GetUserUseCase
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): List<Movie> {
        val accountId = getUserUseCase().id
        return movieRepository.getUserRated(accountId)
    }
}