package com.giraffe.media.movie.usecase

import com.giraffe.media.movie.entity.Movie
import com.giraffe.media.movie.repository.MovieRepository
import com.giraffe.user.exception.AccessDeniedException
import com.giraffe.user.usecase.GetUserUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): List<Movie> {
        val accountId = getUserUseCase().first()?.id ?: throw AccessDeniedException()
        return movieRepository.getUserRated(accountId)
    }
}