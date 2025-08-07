package com.giraffe.media.movies.usecase

import com.giraffe.media.movies.entity.Movie
import com.giraffe.media.movies.repository.MoviesRepository
import com.giraffe.user.usecase.GetUserUseCase
import javax.inject.Inject

class GetRatedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(): Map<Float, Movie> {
        val accountId = getUserUseCase().id
        return repository.getRatedMovies(accountId)
    }
}